package com.example.matchmusic.ui.home

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.matchmusic.Database.AppDatabaseService
import com.example.matchmusic.Model.Usuario
import com.example.matchmusic.R
import com.example.matchmusic.ViewModel.UsuarioViewModel
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var usuarioViewModel: UsuarioViewModel

    companion object{
        var usuario : Usuario? = null
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)

        activity?.let {
            usuarioViewModel = ViewModelProviders.of(it).get(UsuarioViewModel::class.java)
        }
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        usuario = usuarioViewModel.me

        txtNome.text = usuario?.nome + " " + usuario?.sobrenome
        txtPensamento.text = usuario?.pensamento
        txtSobremim.text = usuario?.sobremim
        txtSobre.text = "Sobre ${usuario?.nome} ${usuario?.sobrenome}"

        btnEditarPerfil.setOnClickListener {
            if(eTPensamento.visibility == View.VISIBLE && eTSobremim.visibility == View.VISIBLE) {
                eTPensamento.visibility = View.GONE
                eTSobremim.visibility = View.GONE

                usuario?.pensamento = eTPensamento.text.toString()
                usuario?.sobremim = eTSobremim.text.toString()
                SetupListTask().execute()

                txtPensamento.visibility = View.VISIBLE
                txtSobremim.visibility = View.VISIBLE
            } else {
                eTPensamento.visibility = View.VISIBLE
                eTSobremim.visibility = View.VISIBLE

                txtPensamento.visibility = View.GONE
                txtSobremim.visibility = View.GONE
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class SetupListTask: AsyncTask<
            Unit,
            Unit,
            String
            >(){
        override fun doInBackground(vararg params: Unit): String {
            val appDataBase = context?.let { AppDatabaseService.getInstance(it) }
            try {
                usuario?.let { appDataBase?.usuarioDao()?.atualizarUsuario(it) }
                return "Alterado com sucesso."
            }catch (e : Exception){ return "Ocorreu um erro. Tente novamente." }
        }

        override fun onPostExecute(result: String) {
            txtPensamento.text = usuario?.pensamento
            txtSobremim.text = usuario?.sobremim

            Toast.makeText(context, result, Toast.LENGTH_LONG).show()
        }
    }
}
