package com.example.matchmusic.Fragments

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.matchmusic.Database.AppDatabaseService
import com.example.matchmusic.Model.EmailValidator
import com.example.matchmusic.Model.Usuario

import com.example.matchmusic.R
import kotlinx.android.synthetic.main.fragment_cadastro.*

class CadastroFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cadastro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        enviarButton.setOnClickListener {
            SetupListTask().execute()
        }

        cancelarCadastroButton.setOnClickListener{
            findNavController().navigate(R.id.loginFragment)
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class SetupListTask: AsyncTask<
            Unit,
            Unit,
            List<Any>
            >(){
            override fun doInBackground(vararg params: Unit): List<Any> {
                val appDataBase = context?.let { AppDatabaseService.getInstance(it) }

                val nome = nomeText.text.toString()
                val sobrenome = sobrenomeText.text.toString()
                val email = emailText.text.toString()
                val senha = senhaText.text.toString()

                if(!nome.isEmpty() && !nome.isBlank() && !sobrenome.isEmpty() && !sobrenome.isBlank() && !email.isEmpty() && !email.isBlank()
                    && !email.isEmpty() && !email.isBlank()
                ) {
                    if(nome.length >= 3 && sobrenome.length >= 3){
                        if(EmailValidator.validarEmail(email)){
                            if (senha.length in 8..16) {
                                val usuario = Usuario(nome = nome, sobrenome = sobrenome, email = email, senha = senha)
                                appDataBase?.usuarioDao()?.inserirUsuario(usuario)
                                return listOf("Registrou com sucesso!", true)
                            } else {
                                return listOf("Sua senha deve ter entre 8 e 16 caracteres!", false)
                            }
                        } else {
                            return listOf("Insira um email valido!!", false)
                        }
                    } else {
                        return listOf("Nome e sobrenome devem ter mais que três caracteres", false)
                    }
                }else {
                    return listOf("Dados inválidos!!", false)
                }
            }

            override fun onPostExecute(result: List<Any>?) {
                if(result?.get(1) != false) {
                    findNavController().navigate(R.id.loginFragment)
                }
                Toast.makeText(context, result?.get(0).toString(), Toast.LENGTH_LONG).show()
            }
        }
    }



    /*private fun registrar(appDatabase: AppDatabase?) {
        val nome = nomeText.text.toString()
        val sobrenome = sobrenomeText.text.toString()
        val email = emailText.text.toString()
        val senha = senhaText.text.toString()

        if(!nome.isEmpty() && !nome.isBlank() && !sobrenome.isEmpty() && !sobrenome.isBlank() && !email.isEmpty() && !email.isBlank()
            && !email.isEmpty() && !email.isBlank()
        ) {
            if(nome.length >= 3 && sobrenome.length >= 3){
                if(EmailValidator.validarEmail(email)){
                    if (senha.length >= 8 && senha.length <= 16) {

                        val usuario = Usuario(nome = nome, sobrenome = sobrenome, email = email, senha = senha)
                        appDatabase?.usuarioDao()?.inserirUsuario(usuario)
                        findNavController().navigate(R.id.loginFragment)

                    } else {
                        Toast.makeText(activity,
                            "Sua senha deve ter entre 8 e 16 caracteres!",
                            Toast.LENGTH_LONG)
                            .show()
                    }
                } else {
                    Toast.makeText(activity,
                        "Insira um email valido!!",
                        Toast.LENGTH_LONG)
                        .show()
                }
            } else {
                Toast.makeText(activity,
                    "Nome e sobrenome devem ter mais que três caracteres",
                    Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            Toast.makeText(activity,
                "Dados inválidos!!",
                Toast.LENGTH_LONG)
                .show()
        }
    }

}*/
