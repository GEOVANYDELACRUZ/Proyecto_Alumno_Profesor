package delacruz.examenfinal.proyectofinal.ui.consolidado;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.button.MaterialButton;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import delacruz.examenfinal.proyectofinal.Adaptadores.ConsoAdapter;
import delacruz.examenfinal.proyectofinal.Entidad.Curso;
import delacruz.examenfinal.proyectofinal.Entidad.Notas;
import delacruz.examenfinal.proyectofinal.Instancia.VolleySingleton;
import delacruz.examenfinal.proyectofinal.R;
import delacruz.examenfinal.proyectofinal.Util.UtilDTG;
import delacruz.examenfinal.proyectofinal.databinding.FragmentConsolidadoBinding;


public class Consolidado extends Fragment {
    Spinner spinCursos;
    TextView txtCantEst;
    MaterialButton btnGuardarLis;

    ProgressDialog progreso;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;

    ArrayList<Notas> lstDetNotas;
    ArrayList<Curso> lstCurso;

    String codigoProf,codCurso,cursNombre;

    ListView listConso;
    Button btnDescargar;

    private ConsolidadoViewModel slideshowViewModel;
    private static final String ARCHIVO_PREF = "profesor";
    private FragmentConsolidadoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(ConsolidadoViewModel.class);

        binding = FragmentConsolidadoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        //BLOQUE SHAREDPREFERENCES "DATOS DE PROFESOR"
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(ARCHIVO_PREF,0);
        //        private static final String ARCHIVO_PREF = "profesor";
        codigoProf = sharedPreferences.getString("IDProf","nnn");

        requestQueue = VolleySingleton.getmInstance(getContext()).getRequestQueue();

//        //final TextView textView = binding.textSlideshow;
//        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//       //        textView.setText(s);
//            }
//        });
        spinCursos = root.findViewById(R.id.spiCCursos);
        txtCantEst = root.findViewById(R.id.txtCNumEstudiantes);
        listConso = root.findViewById(R.id.listCONSO);
        btnDescargar = root.findViewById(R.id.btnDescargar);



        lstCurso=new ArrayList<>();
        verCursos();

        lstDetNotas = new ArrayList<>();
        spinCursos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Curso curso=lstCurso.get(position);
                codCurso= curso.getCodigo();
                cursNombre=curso.getCurso();
                verConso();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnDescargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goDescargar();
            }
        });




        return root;
    }

    private void goDescargar() {
        Workbook workbook = new HSSFWorkbook();
        Cell cell= null;
        CellStyle cellStyle= workbook.createCellStyle();
        cellStyle.setFillBackgroundColor(HSSFColor.LIGHT_GREEN.index);
        Sheet sheet= workbook.createSheet("Seguimiento_CRED");
        Row row = null;

        row=sheet.createRow(0);
        row.createCell(0).setCellValue("DNI_ALUMNO");
        row.createCell(1).setCellValue("APELLIDO_PATERNO");
        row.createCell(2).setCellValue("NOMBRES");
        row.createCell(3).setCellValue("NOTA");
        row.createCell(4).setCellValue("CRITERIO");
        int x=0;
        int numfil=lstDetNotas.size();
        for (int a=1;a<=numfil;a++){
            Row fila=sheet.createRow(a);

            cell=fila.createCell(0);
            cell.setCellValue(lstDetNotas.get(x).getDNI());
            cell=fila.createCell(1);
            cell.setCellValue(lstDetNotas.get(x).getApPaterno());
            cell=fila.createCell(2);
            cell.setCellValue(lstDetNotas.get(x).getAlumNombre());
            cell=fila.createCell(3);
            cell.setCellValue(lstDetNotas.get(x).getAlumNota());
            cell=fila.createCell(4);
            cell.setCellValue(lstDetNotas.get(x).getAlumCriterio());
            x++;
        }

        File Ubi= Environment.getExternalStorageDirectory();
        String filename= "CRED.xls";
        File file=new File(Ubi.getAbsolutePath(),"/DCIM");
        file.mkdirs();
        File outfile= new File(file,filename);

        String Ubi1= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString();

        Date fechaActual = new Date();

        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String fechaFormateada = formatoFecha.format(fechaActual);

        String nombreArchivo = cursNombre + " - " + fechaFormateada + ".xls";

        String filename1 = nombreArchivo;

        File file1=new File(Ubi1,filename1);

        try {
            OutputStream fileOutputStream= new FileOutputStream(file1);

            workbook.write(fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Toast.makeText(getContext(), "Archivo descargado: "+filename1, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
        }
    }

    private void verConso() {
        lstDetNotas.clear();
        ConsoAdapter consoAdapter = new ConsoAdapter(getContext(), lstDetNotas);
        listConso.setAdapter(consoAdapter);
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando consolidado...");
        progreso.show();
        String url = UtilDTG.RUTA+"consultarConsolidado.php" +
                "?curso="+codCurso +
                "&prof="+codigoProf;
        url=url.replace(" ","%20");
        Log.e("TT","url ALUMNO - NOTAS: "+url);
//        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progreso.hide();
                Notas notas =null;
                JSONArray json = response.optJSONArray("tblCurso");
                Log.e("TT","Tamaño del jason : "+json.length());
                try {
                    for(int i=0; i<json.length();i++){
                        notas = new Notas();
                        JSONObject jsonObject=null;
                        jsonObject = json.getJSONObject(i);
                        notas.setDNI(jsonObject.getString("DNI"));
                        notas.setAlumCriterio(jsonObject.getString("cursCriterio"));
                        notas.setApPaterno(jsonObject.getString("alumAPaterno"));
                        notas.setAlumNombre(jsonObject.getString("alumNombres"));
                        notas.setAlumNota(jsonObject.getString("cursNota"));
                        lstDetNotas.add(notas);
                    }
                    ConsoAdapter adapterCurso = new ConsoAdapter(getContext(), lstDetNotas);
                    listConso.setAdapter(adapterCurso);
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

    private void verCursos() {
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando alumnos...");
        progreso.show();
        String url = UtilDTG.RUTA+"consultarProfesor.php?profesor="+codigoProf;
        url=url.replace(" ","%20");
        Log.e("TT","url PROFESOR - CONSO: "+url);
//        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progreso.hide();
                Curso curso=null;
                JSONArray json = response.optJSONArray("tblCurso");
                Log.i("TT","Tamaño del jason : "+json.length());
                try {
                    for(int i=0; i<json.length();i++){
                        curso = new Curso();
                        JSONObject jsonObject=null;
                        jsonObject = json.getJSONObject(i);
                        curso.setCodigo(jsonObject.getString("IDCurso"));
                        curso.setCurso(jsonObject.getString("curNombre"));
//                        Toast.makeText(getContext(), "Curso : "+curso.getCurso(), Toast.LENGTH_SHORT).show();
                        lstCurso.add(curso);
                    }
                    ArrayAdapter<Curso> adapter = new ArrayAdapter<Curso>(getContext(), android.R.layout.simple_spinner_item, lstCurso);
                    spinCursos.setAdapter(adapter);
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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}