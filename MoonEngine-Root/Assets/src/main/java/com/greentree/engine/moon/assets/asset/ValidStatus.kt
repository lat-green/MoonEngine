package com.greentree.engine.moon.assets.asset

enum class ValidStatus {

	NEVER_VALID {

		override fun times(other: ValidStatus) = this
		override fun plus(other: ValidStatus) = other
		override fun asNotEver() = NOT_VALID
		override val isValid
			get() = false
	},
	NOT_VALID {

		override fun times(other: ValidStatus) = when(other) {
			NEVER_VALID -> NEVER_VALID
			else -> this
		}

		override fun plus(other: ValidStatus) = when(other) {
			VALID -> VALID
			EVER_VALID -> EVER_VALID
			else -> this
		}

		override val isValid
			get() = false
	},
	VALID {

		override fun times(other: ValidStatus) = when(other) {
			NEVER_VALID -> NEVER_VALID
			NOT_VALID -> NOT_VALID
			else -> this
		}

		override fun plus(other: ValidStatus) = when(other) {
			EVER_VALID -> EVER_VALID
			else -> this
		}

		override val isValid
			get() = true
	},
	EVER_VALID {

		override fun times(other: ValidStatus) = other

		override fun plus(other: ValidStatus) = this
		override val isValid
			get() = true

		override fun asNotEver() = VALID
	},
	;

	abstract operator fun times(other: ValidStatus): ValidStatus
	abstract operator fun plus(other: ValidStatus): ValidStatus
	abstract val isValid: Boolean
	open fun asNotEver(): ValidStatus = this

	companion object {

		fun get(value: Boolean) = if(value) VALID else NOT_VALID
	}
}

operator fun ValidStatus.plus(other: Boolean) = plus(ValidStatus.get(other))
operator fun Boolean.plus(other: ValidStatus) = other.plus(this)
operator fun ValidStatus.times(other: Boolean) = times(ValidStatus.get(other))
operator fun Boolean.times(other: ValidStatus) = other.times(this)