package delacruz.examenfinal.alumnoapp.Adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import delacruz.examenfinal.alumnoapp.Entidades.Notas;
import delacruz.examenfinal.alumnoapp.Instancia.VolleySingleton;
import delacruz.examenfinal.alumnoapp.MainActivity;
import delacruz.examenfinal.alumnoapp.R;
import delacruz.examenfinal.alumnoapp.Util.UtilDTG;
import delacruz.examenfinal.alumnoapp.ui.VisualizarNotas.visualizarnotaFragment;

public class AdapterCurso extends BaseAdapter {
    private Context context;
    private ArrayList<Notas> arrayList;
    ProgressDialog progreso;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;
    public AdapterCurso() {
    }

    public AdapterCurso(Context context, ArrayList<Notas> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.item,null);
        }
        TextView txvValor = convertView.findViewById(R.id.txtValor);
        TextView txvCrit = convertView.findViewById(R.id.txtCriterio);
        TextView txvDesc = convertView.findViewById(R.id.txtDescr);
        Button btnComment = convertView.findViewById(R.id.btnComment);


        txvValor.setText(arrayList.get(position).getValor());
        txvCrit.setText(arrayList.get(position).getCriterio());
        txvDesc.setText(arrayList.get(position).getDescripcion());

        txvValor.setTextSize(20);
        requestQueue = VolleySingleton.getmInstance(context).getRequestQueue();
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment(position);
            }
        });
        return convertView;
    }

    private void comment(Integer position) {
//        Toast.makeText(context, "Prueba: "+arrayList.get(position).getCodigo(), Toast.LENGTH_SHORT).show();
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
        txvCursoNota.setText(arrayList.get(position).getCurso()+" - "+arrayList.get(position).getCriterio());

        editText.setEnabled(false);
        editResp.setEnabled(false);
        if (!arrayList.get(position).getCursNComment().replace(" ", "").equals("")) {
            btnEnviar.setVisibility(View.GONE);
            editText.setText(arrayList.get(position).getCursNComment());
        }else{
            editText.setEnabled(true);
            btnEnviar.setVisibility(View.VISIBLE);
        }
        if (!arrayList.get(position).getCursNRespuesta().replace(" ", "").equals("")) {
            editResp.setText(arrayList.get(position).getCursNRespuesta());
        }else{
            editResp.setText("SIN RESPUESTA");
        }
        // Configurar clics de los botones (puedes agregar tu lógica aquí)
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica del botón Aceptar
//                Toast.makeText(context, "ACEPTAR: "+arrayList.get(position).getCodigo(), Toast.LENGTH_SHORT).show();
                if(!editText.getText().toString().replace(" ","").equals("")){
                    agregarComentario(position,String.valueOf(editText.getText()),dialog);
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
        String url = UtilDTG.RUTA+"insertarComentario.php"+
                "?cod=" + arrayList.get(position).getCodigo() +
                "&comentario=" + comentario;
        url=url.replace(" ","%20");
        Log.d("Url : ",url.toString());

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progreso.hide();
                String msj = response.optString("msj");
                if (msj.equals("0")) msj=context.getString(R.string.error_msj1);
                if (msj.equals("1")){
                    msj=context.getString(R.string.msj1);
                    arrayList.get(position).setCursNComment(comentario);
                    dialog.dismiss();
                }
                Toast.makeText(context, msj, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, context.getString(R.string.error_General), Toast.LENGTH_SHORT).show();
                Log.e(" ERROR: ", error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
