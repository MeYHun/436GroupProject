package com.malkinfo.editingrecyclerview


    class Validators {
        fun validEmail(email: String?): Boolean {
            if (email.isNullOrEmpty()) {
                return false
            }

            // General Email Regex (RFC 5322 Official Standard)
            val emailRegex = Regex(
                "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'" +
                        "*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x" +
                        "5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@terpmail.umd.edu"
            )
            return emailRegex.matches(email)
        }
    }

