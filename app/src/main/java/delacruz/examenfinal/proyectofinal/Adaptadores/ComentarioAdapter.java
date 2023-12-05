package delacruz.examenfinal.proyectofinal.Adaptadores;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;

import delacruz.examenfinal.proyectofinal.Entidad.Comentario;
import delacruz.examenfinal.proyectofinal.Instancia.VolleySingleton;
import delacruz.examenfinal.proyectofinal.R;
import delacruz.examenfinal.proyectofinal.Util.UtilDTG;

public class ComentarioAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Comentario> arrayList;
    ProgressDialog progreso;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;

    public ComentarioAdapter() {
    }

    public ComentarioAdapter(Context context, ArrayList<Comentario> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(R.layout.itemcomentario,null);
        }
        TextView txvNombre = view.findViewById(R.id.txvNombre);
        TextView txvComentario = view.findViewById(R.id.txvComentario);
        TextView txvCurso = view.findViewById(R.id.txvCurso);
        TextView txvCriterio = view.findViewById(R.id.txvCriterio);
        TextView txvNota = view.findViewById(R.id.txvNota);
        TextView txvResp = view.findViewById(R.id.txvResp);
        Button btnResp = view.findViewById(R.id.btn_responder);
        requestQueue = VolleySingleton.getmInstance(context).getRequestQueue();

        txvNombre.setText(arrayList.get(i).getAlumno());
        txvComentario.setText(arrayList.get(i).getCursNComment());
        txvCurso.setText(arrayList.get(i).getCurNombre());
        txvCriterio.setText(arrayList.get(i).getCursCriterio());
        txvNota.setText(arrayList.get(i).getCursNota());

        String resp = arrayList.get(i).getCursNRespuesta();
        txvResp.setVisibility(View.GONE);
        btnResp.setVisibility(View.GONE);
        if(resp.replace(" ","").equals("null")){
            btnResp.setVisibility(View.VISIBLE);
        }
        else {
            txvResp.setText(resp);
            txvResp.setVisibility(View.VISIBLE);
        };
        btnResp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goResp(i);
            }
        });
        return view;
    }

    private void goResp(int position) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // Ocultar el título del diálogo
        dialog.setContentView(R.layout.item_comment); // Inflar el diseño XML
        Window window = dialog.getWindow();
        if (window != null) {
            // Configurar los atributos de diseño para que ocupe toda la pantalla
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.BOTTOM);  // Opcional: Puedes ajustar la gravedad según tus necesidades
        }

        // Configurar los botones del diálogo
        TextView txvCursoNota = dialog.findViewById(R.id.txvCursoNota);
        Button btnEnviar = dialog.findViewById(R.id.btnEnviar);
        Button btnCerrar = dialog.findViewById(R.id.btnCerrar);
        EditText editText = dialog.findViewById(R.id.etxComment);
        EditText editResp = dialog.findViewById(R.id.etxResp);
        final TextView characterCount = dialog.findViewById(R.id.characterCount);
        txvCursoNota.setText(arrayList.get(position).getCurNombre()+" - "+arrayList.get(position).getCursCriterio());

        editText.setEnabled(false);
        editResp.setEnabled(false);
        editText.setText(arrayList.get(position).getCursNComment());
        btnEnviar.setVisibility(View.GONE);
        if (arrayList.get(position).getCursNRespuesta().equals("null")){
            editResp.setEnabled(true);
            btnEnviar.setVisibility(View.VISIBLE);
        }else{
            editResp.setText(arrayList.get(position).getCursNRespuesta());
        }
        // Configurar clics de los botones (puedes agregar tu lógica aquí)
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica del botón Aceptar
//                Toast.makeText(context, "ACEPTAR: "+arrayList.get(position).getCodigo(), Toast.LENGTH_SHORT).show();
                if(!editResp.getText().toString().replace(" ","").equals("")){
                    agregarComentario(position,String.valueOf(editResp.getText()),dialog);
                }else{
                    Toast.makeText(context, "Es nulo y no puede registrar el campo vacío.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica del botón Cancelar
                dialog.dismiss(); // Cerrar el diálogo al hacer clic en Cancelar
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                // No necesario para esta implementación
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Actualizar el contador de caracteres
                int currentLength = charSequence.length();
                characterCount.setText(String.format("%d/255", currentLength));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No necesario para esta implementación
            }
        });
        // Hacer que el diálogo no sea cancelable al tocar fuera del diálogo
        dialog.setCanceledOnTouchOutside(false);

        // Mostrar el diálogo
        dialog.show();
    }


    private void agregarComentario(Integer position, String comentario,Dialog dialog) {
        progreso = new ProgressDialog(context);
        progreso.setMessage(context.getString(R.string.load_Register));
        progreso.show();
        String url = UtilDTG.RUTA+"actualizarComentario.php" +
                "?codC=" + arrayList.get(position).getIDCursNota()  +
                "&coment="+ comentario;
        url=url.replace(" ","%20");
        Log.d("Url : ",url.toString());
        arrayList.get(position).setCursNRespuesta(comentario);
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progreso.hide();
                String msj = response.optString("msj");
                if (msj.equals("0")) msj=context.getString(R.string.error_msj1);
                if (msj.equals("1")){
                    msj=context.getString(R.string.msj1);
                    arrayList.get(position).setCursNRespuesta(comentario);
                    dialog.dismiss();
                }
                Toast.makeText(context, msj, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(context, context.getString(R.string.error_General), Toast.LENGTH_SHORT).show();
                Log.e(" ERROR: ", error.toString());
                dialog.dismiss();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
