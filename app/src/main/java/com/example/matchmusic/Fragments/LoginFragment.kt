package com.example.matchmusic.Fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.matchmusic.Database.AppDatabaseService
import com.example.matchmusic.GeneroActivity
import com.example.matchmusic.MainActivity
import com.example.matchmusic.Model.Genero
import com.example.matchmusic.Model.Usuario
import com.example.matchmusic.Model.UsuarioEGenero

import com.example.matchmusic.R
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        criarConta.setOnClickListener {
            findNavController().navigate(R.id.cadastroFragment)
        }

        loginButton.setOnClickListener {
            SetupListTask().execute()
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class SetupListTask: AsyncTask<
            Unit,
            Unit,
            List<Any?>
            >(){
        override fun doInBackground(vararg params: Unit): List<Any?> {
            val appDatabase = context?.let { it1 -> AppDatabaseService.getInstance(it1) }

            var usuario : Array<UsuarioEGenero>? = null
            val email : String = emailLogin.text.toString()
            val senha : String = senhaLogin.text.toString()

            if(email.isNotEmpty() && senha.isNotEmpty()){
                val userexists = appDatabase?.usuarioDao()?.validarUsuario(email, senha)
                if(userexists?.size == 0){
                    return listOf("Não há nenhuma conta com este email e senha.", null, false)
                } else {
                    try {
                        usuario = userexists?.get(0)?.id?.let {
                            appDatabase.usuarioDao().pegarUsuarioeGeneros(
                                it
                            )
                        }
                    }catch (e: Exception) { return listOf("Usuario do banco de dados não possui genero.", userexists?.get(0), true) }

                    return listOf("Usuario possui gênero.", usuario?.get(0), true)
                }
            } else {
                return listOf("Campos em branco.", null, false)
            }
        }

        override fun onPostExecute(result: List<Any?>) {
            if(result.get(2) == true){
                var intt : Intent?
                var usuario_e_genero : UsuarioEGenero? = null
                var usuario : Usuario? = null

                try{
                    usuario_e_genero = result[1] as UsuarioEGenero
                }catch (e: Exception){ usuario = result[1] as Usuario }

                if(usuario_e_genero != null){
                    intt = Intent(activity, MainActivity::class.java)
                    intt.putExtra("usuario", usuario_e_genero.usuario)
                } else {
                    intt = Intent(activity, GeneroActivity::class.java)
                    intt.putExtra("usuario", usuario)
                }
                startActivity(intt)
            }
        }
    }

    /*private fun autenticar(appDatabase: AppDatabase?) {
        //var sp = getSharedPreferences("login", MODE_PRIVATE)
        //val sp : SharedPreferences = this.getSharedPreferences("login", MODE_PRIVATE)
        val email : String = emailLogin.text.toString()
        val senha : String = senhaLogin.text.toString()

        if(email.isNotEmpty() && senha.isNotEmpty()){
            val userexists = appDatabase?.usuarioDao()?.validarUsuario(email, senha)
            if(userexists?.size == 0){
                Toast.makeText(context, "Não há nenhuma conta com este email e senha...", Toast.LENGTH_LONG).show()
            } else {
                val genreexists = userexists?.get(0)?.id?.let {
                    appDatabase.generoDao().pegarGeneroUsuario(
                        it
                    )
                }
                var intt : Intent?

                if(genreexists?.size == 0){
                    intt = Intent(activity, GeneroActivity::class.java)
                }else{
                    intt = Intent(activity, MainActivity::class.java)
                }
                intt.putExtra("usuario", userexists?.get(0))
                startActivity(intt)
            }
        }
    }*/

}
