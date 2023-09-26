package delacruz.examenfinal.proyectofinal.ui.configuraciones;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import delacruz.examenfinal.proyectofinal.R;

public class Configuraciones extends Fragment {

    private ConfiguracionesViewModel mViewModel;

    public static Configuraciones newInstance() {
        return new Configuraciones();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.configuraciones_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ConfiguracionesViewModel.class);
        // TODO: Use the ViewModel
    }

}