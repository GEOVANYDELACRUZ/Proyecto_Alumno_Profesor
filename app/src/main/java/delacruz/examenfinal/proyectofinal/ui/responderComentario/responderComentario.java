package delacruz.examenfinal.proyectofinal.ui.responderComentario;

import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import delacruz.examenfinal.proyectofinal.Adaptadores.ComentarioAdapter;
import delacruz.examenfinal.proyectofinal.Adaptadores.ConsoAdapter;
import delacruz.examenfinal.proyectofinal.Entidad.Comentario;
import delacruz.examenfinal.proyectofinal.Entidad.Notas;
import delacruz.examenfinal.proyectofinal.Instancia.VolleySingleton;
import delacruz.examenfinal.proyectofinal.R;
import delacruz.examenfinal.proyectofinal.Util.UtilDTG;
import delacruz.examenfinal.proyectofinal.databinding.FragmentConsolidadoBinding;
import delacruz.examenfinal.proyectofinal.databinding.FragmentResponderComentarioBinding;
import delacruz.examenfinal.proyectofinal.ui.consolidado.ConsolidadoViewModel;

public class responderComentario extends Fragment {
    ListView lst;
    ArrayList <Comentario> lstComentario;
    String codigoProf;
    private static final String ARCHIVO_PREF = "profesor";

    ProgressDialog progreso;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;
    private ResponderComentarioViewModel mViewModel;
    private FragmentResponderComentarioBinding binding;

    public static responderComentario newInstance() {
        return new responderComentario();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel =
                new ViewModelProvider(this).get(ResponderComentarioViewModel.class);

        binding = FragmentResponderComentarioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        requestQueue = VolleySingleton.getmInstance(getContext()).getRequestQueue();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(ARCHIVO_PREF,0);
        codigoProf = sharedPreferences.getString("IDProf","nnn");
        lstComentario = new ArrayList<>();
        lst = root.findViewById(R.id.lstComentario);
        cargarComentarios();
        return root;
    }

    private void cargarComentarios() {
        lstComentario.clear();
        lst.setAdapter(new ComentarioAdapter(getContext(),lstComentario));
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando consolidado...");
        progreso.show();
        String url = UtilDTG.RUTA+"consultarComentarioProfesor.php?cod="+codigoProf;
        url=url.replace(" ","%20");
        Log.e("TT","url ALUMNO - NOTAS: "+url);
//        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progreso.hide();
                Comentario comentario = null;
                JSONArray json = response.optJSONArray("tblCurso");
                Log.e("TT","Tamaño del jason : "+json.length());
                try {
                    for(int i=0; i<json.length();i++){
                        comentario = new Comentario();
                        JSONObject jsonObject=null;
                        jsonObject = json.getJSONObject(i);
                        comentario.setCurNombre(jsonObject.getString("curNombre"));
                        comentario.setIDAlumC(jsonObject.getString("IDAlumC"));
                        comentario.setAlumno(jsonObject.getString("Alumno"));
                        comentario.setCursNota(jsonObject.getString("cursNota"));
                        comentario.setCursCriterio(jsonObject.getString("cursCriterio"));
                        comentario.setCursNComment(jsonObject.getString("cursNComment"));
                        comentario.setCursNRespuesta(jsonObject.getString("cursNRespuesta"));
                        comentario.setIDCursNota(jsonObject.getString("IDCursNota"));
                        lstComentario.add(comentario);

                    }
                    ComentarioAdapter comentarioAdapter = new ComentarioAdapter(getContext(), lstComentario);
                    lst.setAdapter(comentarioAdapter);
                }catch (Exception e){
                    Log.i("TT","Error al listar CURSOS : "+e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                Log.i("TT","ERROR AL LISTAR/CURSO: "+error);

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ResponderComentarioViewModel.class);
        // TODO: Use the ViewModel
    }

}