package com.greentree.engine.moon.ecs.system.debug;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.greentree.commons.util.function.CheckedSupplier;
import com.greentree.engine.moon.ecs.system.ECSSystem;

public class PrintStreamSystemsProfiler implements SystemsProfiler {
	private static final long serialVersionUID = 1L;

	private final Supplier<? extends PrintStream> out_init, out_destroy, out_update;

	public PrintStreamSystemsProfiler(File out_init, File out_update, File out_destroy) throws FileNotFoundException {
		this(cerateSupplier(out_init), cerateSupplier(out_update), cerateSupplier(out_destroy));
	}

	private static CheckedSupplier<? extends PrintStream> cerateSupplier(File file) {
		file.delete();
		return () -> new PrintStream(new FileOutputStream(file, true));
	}

	public PrintStreamSystemsProfiler(Supplier<? extends PrintStream> out_init, Supplier<? extends PrintStream> out_update, Supplier<? extends PrintStream> out_destroy) {
		this.out_init = out_init;
		this.out_update = out_update;
		this.out_destroy = out_destroy;
	}
	public PrintStreamSystemsProfiler(CheckedSupplier<? extends PrintStream> out_init, CheckedSupplier<? extends PrintStream> out_update, CheckedSupplier<? extends PrintStream> out_destroy) {
		this.out_init = out_init.toNonCheked();
		this.out_update = out_update.toNonCheked();
		this.out_destroy = out_destroy.toNonCheked();
	}

	@Override
	public MethodSystemsProfiler destroy() {
		return new MyMethodSystemsProfiler(out_destroy);
	}

	@Override
	public MethodSystemsProfiler init() {
		return new MyMethodSystemsProfiler(out_init);
	}

	@Override
	public MethodSystemsProfiler update() {
		return new MyMethodSystemsProfiler(out_update);
	}

	private final class MyMethodSystemsProfiler implements MethodSystemsProfiler {

		private final List<ResultCall> results = new ArrayList<>();

		private final Supplier<? extends PrintStream> outSupplier;
		
		public MyMethodSystemsProfiler(Supplier<? extends PrintStream> outSupplier) {
			this.outSupplier = outSupplier;
		}

		@Override
		public void close() {
			var methodTime = 0f;
			for(var result : results) {
				final var time = (result.finish - result.start) / 1_000_000_000f;
				methodTime += time;
			}

			try(final var out = outSupplier.get();) {	
    			for(var result : results) {
    				final var time = (result.finish - result.start) / 1_000_000_000f;
    				out.print(result.system.getClass().getName());
    				out.print(":");
    				out.print(time / methodTime * 100);
    				out.print('\n');
    				out.print("time:");
    				out.print(time);
    				out.print('\n');
    			}
    			out.println("global time:"+methodTime);
    			out.println();
			}
		}

		@Override
		public StartSystemsProfiler start(ECSSystem s) {
			return new MyStartSystemsProfiler(s);
		}

		public class MyStartSystemsProfiler implements StartSystemsProfiler {

			private final long start;
			private final ECSSystem system;

			public MyStartSystemsProfiler(ECSSystem system) {
				this.system = system;
				start = System.nanoTime();
			}

			@Override
			public void close() {
				final var finish = System.nanoTime();
				final var result = new ResultCall(system, start, finish);
				results.add(result);
			}

		}

	}

	private record ResultCall(ECSSystem system, long start, long finish) {
	}

}
