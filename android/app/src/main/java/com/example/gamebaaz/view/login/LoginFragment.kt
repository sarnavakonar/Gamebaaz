package com.example.gamebaaz.view.login

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.gamebaaz.R
import com.example.gamebaaz.ui.composables.*
import com.example.gamebaaz.ui.theme.GamebaazTheme
import com.example.gamebaaz.ui.theme.Purple100
import com.example.gamebaaz.ui.theme.Purple200
import com.example.gamebaaz.util.Status
import com.example.gamebaaz.view.GameViewModel
import com.example.gamebaaz.view.MainActivity

class LoginFragment: Fragment() {

    private val viewModel: GameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                LoginUI(window = requireActivity().window, viewModel = viewModel)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    @Composable
    fun LoginUI(window: Window, viewModel: GameViewModel) {

        val context = LocalContext.current
        val focusManager = LocalFocusManager.current
        val username = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }
        val showLoader = remember { mutableStateOf(false) }
        val integerChars = '0'..'9'

        viewModel.getLoginResponse().observe(viewLifecycleOwner, {
            when(it.status){
                Status.SUCCESS -> {
                    showLoader.value = false
                    if(it.data?.status == "SUCCESS"){
                        startActivity(Intent(context, MainActivity::class.java))
                    }
                    else
                        Toast.makeText(context, "${it.data?.message}", Toast.LENGTH_SHORT).show()
                }
                Status.ERROR -> {
                    showLoader.value = false
                    val error = viewModel.getLoginResponse().value?.message
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    showLoader.value = true
                }
            }
        })

        GamebaazTheme(window = window) {
            Surface(
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
            ) {

                Image(
                    painter = painterResource(id = R.drawable.login_bg),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )

                val screenGradient = Brush.verticalGradient(
                    listOf(
                        Color.Transparent,
                        Color.Transparent,
                        Color.Transparent,
                        Color.Black,
                        Color.Black
                    )
                )
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(
                        screenGradient
                    )
                ) {

                }

                if (showLoader.value)
                    DrawLoaderBox(
                        contentAlignment = Alignment.Center
                    )

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom
                ) {

                    TextField(
                        value = username.value,
                        onValueChange = {
                            if (it.all { it in integerChars })
                                username.value = it
                        },
                        singleLine = true,
                        maxLines = 1,
                        placeholder = {
                            Text(
                                text = "Phone number",
                                style = MaterialTheme.typography.button.copy(
                                    color = Color.White.copy(alpha = 0.375f)
                                )
                            )},
                        textStyle = MaterialTheme.typography.button.copy(color = Color.White),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp)
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .border(
                                width = 1.dp,
                                color = Purple200,
                                shape = MaterialTheme.shapes.medium
                            ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        keyboardActions = KeyboardActions(onNext = { focusManager.clearFocus() })
                    )

                    TextField(
                        value = password.value,
                        onValueChange = {
                            password.value = it
                        },
                        singleLine = true,
                        maxLines = 1,
                        placeholder = {
                            Text(
                                text = "Password",
                                style = MaterialTheme.typography.button.copy(
                                    color = Color.White.copy(alpha = 0.375f)
                                )
                            )},
                        textStyle = MaterialTheme.typography.button.copy(color = Color.White),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .border(
                                width = 1.dp,
                                color = Purple200,
                                shape = MaterialTheme.shapes.medium
                            ),
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
                    )

                    val gradient = Brush.horizontalGradient(
                        listOf(
                            Purple200,
                            Purple100
                        )
                    )
                    GradientButton(
                        text = "SIGN IN",
                        textColor = Color.White,
                        gradient = gradient,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp),
                        onClick = {
                            if(password.value.isNotEmpty() && username.value.isNotEmpty())
                                viewModel.createUser(
                                    username = username.value,
                                    password = password.value
                                )
                        }
                    )
                }
            }
        }
    }

}