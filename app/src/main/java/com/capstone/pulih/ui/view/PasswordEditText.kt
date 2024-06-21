package com.pulihproject.pulih.ui.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.capstone.pulih.R

class PasswordEditText: AppCompatEditText, View.OnTouchListener {
    private lateinit var errorIcon: Drawable
    private lateinit var passwordIcon: Drawable

    // Constructor
    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    // Isi Constructor
    private fun init() {
        errorIcon = ContextCompat.getDrawable(context, R.drawable.ic_error) as Drawable
        passwordIcon = ContextCompat.getDrawable(context, R.drawable.password_icon_draw) as Drawable
        setOnTouchListener(this)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().length < 8) {
                    setError("Password harus lebih dari 8 karakter",null)
                    setCompoundDrawablesWithIntrinsicBounds(passwordIcon, null, errorIcon, null)
                } else {
                    setCompoundDrawablesWithIntrinsicBounds(passwordIcon, null, null, null)
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        return false
    }
}