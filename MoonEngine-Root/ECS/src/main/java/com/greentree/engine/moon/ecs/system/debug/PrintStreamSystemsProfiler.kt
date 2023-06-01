package com.greentree.engine.moon.ecs.system.debug

import com.greentree.commons.util.function.CheckedSupplier
import com.greentree.engine.moon.ecs.system.ECSSystem
import java.io.File
import java.io.FileOutputStream
import java.io.PrintStream
import java.util.function.Supplier

class PrintStreamSystemsProfiler : SystemsProfiler {

	private val out_init: Supplier<out PrintStream>
	private val out_destroy: Supplier<out PrintStream>
	private val out_update: Supplier<out PrintStream>

	constructor(out_init: File, out_update: File, out_destroy: File) : this(
		cerateSupplier(out_init),
		cerateSupplier(out_update),
		cerateSupplier(out_destroy)
	)

	constructor(
		out_init: Supplier<out PrintStream>,
		out_update: Supplier<out PrintStream>,
		out_destroy: Supplier<out PrintStream>
	) {
		this.out_init = out_init
		this.out_update = out_update
		this.out_destroy = out_destroy
	}

	constructor(
		out_init: CheckedSupplier<out PrintStream>,
		out_update: CheckedSupplier<out PrintStream>,
		out_destroy: CheckedSupplier<out PrintStream>
	) {
		this.out_init = out_init
		this.out_update = out_update
		this.out_destroy = out_destroy
	}

	override fun destroy(): MethodSystemsProfiler {
		return MyMethodSystemsProfiler(out_destroy)
	}

	override fun init(): MethodSystemsProfiler {
		return MyMethodSystemsProfiler(out_init)
	}

	override fun update(): MethodSystemsProfiler {
		return MyMethodSystemsProfiler(out_update)
	}

	private inner class MyMethodSystemsProfiler(private val outSupplier: Supplier<out PrintStream>) :
		MethodSystemsProfiler {

		private val results: MutableList<ResultCall> = ArrayList()
		override fun close() {
			var methodTime = 0f
			for ((_, start, finish) in results) {
				val time = (finish - start) / 1000000000f
				methodTime += time
			}
			outSupplier.get().use { out ->
				for ((system, start, finish) in results) {
					val time = (finish - start) / 1000000000f
					out.print(system.javaClass.name)
					out.print(":")
					out.print(time / methodTime * 100)
					out.print('\n')
					out.print("time:")
					out.print(time)
					out.print('\n')
				}
				out.println("global time:$methodTime")
				out.println()
			}
		}

		override fun start(s: ECSSystem): StartSystemsProfiler {
			return MyStartSystemsProfiler(s)
		}

		inner class MyStartSystemsProfiler(private val system: ECSSystem) : StartSystemsProfiler {

			private val start: Long

			init {
				start = System.nanoTime()
			}

			override fun close() {
				val finish = System.nanoTime()
				val result = ResultCall(system, start, finish)
				results.add(result)
			}
		}
	}

	@JvmRecord
	private data class ResultCall(val system: ECSSystem, val start: Long, val finish: Long)
	companion object {

		private const val serialVersionUID = 1L
		private fun cerateSupplier(file: File): CheckedSupplier<out PrintStream> {
			file.delete()
			return CheckedSupplier { PrintStream(FileOutputStream(file, true)) }
		}
	}
}