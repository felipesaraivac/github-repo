package com.saraiva.github.ui.screens.util

import android.text.format.DateFormat
import com.saraiva.github.core.Constants
import java.util.Date


fun Date.toUTC() =
    DateFormat.format(Constants.DATEFORMAT, this).toString()