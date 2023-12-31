package com.greentree.engine.moon.assets.bundle

import java.io.EOFException
import java.io.InputStream
import java.io.OutputStream

fun OutputStream.writeUTF(value: String) {
	write(value.toByteArray())
	write(0)
}

fun OutputStream.writeBoolean(value: Boolean) {
	write(if(value) 1 else 0)
}

fun OutputStream.writeShort(value: Short) {
	val value = value.toInt()
	write(value ushr 8 and 0xFF)
	write(value ushr 0 and 0xFF)
}

fun OutputStream.writeByte(value: Byte) {
	val value = value.toInt()
	write(value and 0xFF)
}

fun OutputStream.writeChar(value: Char) {
	val value = value.code
	write(value ushr 8 and 0xFF)
	write(value ushr 0 and 0xFF)
}

fun OutputStream.writeInt(value: Int) {
	write(value ushr 24 and 0xFF)
	write(value ushr 16 and 0xFF)
	write(value ushr 8 and 0xFF)
	write(value ushr 0 and 0xFF)
}

fun OutputStream.writeFloat(value: Float) {
	writeInt(value.toRawBits())
}

fun OutputStream.writeDouble(value: Double) {
	writeLong(value.toRawBits())
}

fun OutputStream.writeLong(value: Long) {
	fun write(b: Long) = write(b.toInt())
	write(value ushr 56 and 0xFF)
	write(value ushr 48 and 0xFF)
	write(value ushr 40 and 0xFF)
	write(value ushr 32 and 0xFF)
	write(value ushr 24 and 0xFF)
	write(value ushr 16 and 0xFF)
	write(value ushr 8 and 0xFF)
	write(value ushr 0 and 0xFF)
}

fun InputStream.readUTF(): String {
	val bytes = mutableListOf<Byte>()
	var byte: Byte
	while(readOrEOF().also { byte = it.toByte() } != 0) {
		bytes.add(byte)
	}
	return String(bytes.toByteArray())
}

fun InputStream.readBoolean(): Boolean {
	return readOrEOF() == 1
}

fun InputStream.readInt(): Int {
	val a = readOrEOF()
	val b = readOrEOF()
	val c = readOrEOF()
	val d = readOrEOF()
	return (a shl 24) or (b shl 16) or (c shl 8) or d
}

fun InputStream.readLong(): Long {
	val a = readOrEOF().toLong()
	val b = readOrEOF().toLong()
	val c = readOrEOF().toLong()
	val d = readOrEOF().toLong()
	val e = readOrEOF().toLong()
	val f = readOrEOF().toLong()
	val g = readOrEOF().toLong()
	val k = readOrEOF().toLong()
	return (a shl 56) or (b shl 48) or (c shl 40) or (d shl 32) or (e shl 24) or (f shl 16) or (g shl 8) or k
}

fun InputStream.readShort(): Short {
	val a = readOrEOF()
	val b = readOrEOF()
	return ((a shl 8) or b).toShort()
}

fun InputStream.readByte(): Byte {
	val a = readOrEOF()
	return a.toByte()
}

fun InputStream.readOrEOF() = run {
	val b = read()
	if(b == -1)
		throw EOFException()
	b
}