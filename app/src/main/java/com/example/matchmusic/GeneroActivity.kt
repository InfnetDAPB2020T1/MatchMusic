package com.example.matchmusic

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import androidx.room.Room
import com.example.matchmusic.Database.AppDatabase
import com.example.matchmusic.Database.AppDatabaseService
import com.example.matchmusic.Model.Genero
import com.example.matchmusic.Model.Usuario
import kotlinx.android.synthetic.main.activity_genero.*

class GeneroActivity : AppCompatActivity() {

    companion object{
        var listaGenero : MutableList<CheckBox>? = null
        var usuario : Usuario? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genero)

        listaGenero = mutableListOf<CheckBox>(
            findViewById(R.id.Rock), findViewById(R.id.Funk),
            findViewById(R.id.Forro), findViewById(R.id.MPB),
            findViewById(R.id.Pop), findViewById(R.id.Eletronica),
            findViewById(R.id.Sertanejo), findViewById(R.id.Samba), findViewById(R.id.Pagode)
            //findViewById(R.id.Gospel), findViewById(R.id.Classico), findViewById(R.id.Outro)
        )

        usuario = intent.getSerializableExtra("usuario") as Usuario

        GeneroButton.setOnClickListener {
            SetupListTask().execute()
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class SetupListTask: AsyncTask<
            Unit,
            Unit,
            List<Any>
            >(){
        override fun doInBackground(vararg params: Unit): List<Any> {
            val appDataBase = AppDatabaseService.getInstance(applicationContext)
            var contador : Int = 0

            listaGenero?.forEach{
                if(it.isChecked){
                    val genero = usuario?.id?.let { it1 -> Genero(it.text.toString(), it1) }

                    if (genero != null) {
                        appDataBase.generoDao().inserirGenero(genero)
                    }
                    contador += 1
                }
            }
            return if(contador >= 3){
                listOf("Generos adicionados com sucesso!", true)
            } else {
                listOf("Selecione ao menos 3 generos.", false)
            }
        }

        override fun onPostExecute(result: List<Any>) {
            if(result.get(1) == true){
                val intt = Intent(this@GeneroActivity, MainActivity::class.java)
                intt.putExtra("usuario", usuario)
                startActivity(intt)
            } else {
                Toast.makeText(applicationContext, result.get(0).toString(), Toast.LENGTH_LONG).show()
            }
        }
    }
    /*private fun guardarGeneros(
        listaGenero: MutableList<CheckBox>,
        usuario: Usuario,
        appDatabase: AppDatabase
    ) {
        var contador : Int = 0

        listaGenero.forEach{
            if(it.isChecked){
                val genero = usuario.id?.let { it1 -> Genero(it.text.toString(), it1) }

                if (genero != null) {
                    appDatabase.generoDao().inserirGenero(genero)
                }
                contador += 1
            }
        }
        if(contador >= 3){

            val intt = Intent(this, MainActivity::class.java)
            intt.putExtra("usuario", usuario)
            startActivity(intt)

        } else {
            Toast.makeText(applicationContext,
                "Selecione ao menos 3 generos.",
                Toast.LENGTH_LONG)
                .show()
        }
    }*/
}
