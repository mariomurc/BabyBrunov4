package me.parzibyte.crudsqlite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.parzibyte.crudsqlite.controllers.TallasController;
import me.parzibyte.crudsqlite.modelos.Talla;

 
public class TallasMainActivity extends AppCompatActivity {
    private List<Talla> listaDeTallas;
    private RecyclerView recyclerView;
    private AdaptadorTallas adaptadorTallas;
    private TallasController tallasController;
    private FloatingActionButton fabAgregarCaca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Ojo: este código es generado automáticamente, pone la vista y ya, pero
        // no tiene nada que ver con el código que vamos a escribir
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tallas_main);


        // Lo siguiente sí es nuestro ;)
        // Definir nuestro controlador
        tallasController = new TallasController(TallasMainActivity.this);

        // Instanciar vistas
        recyclerView = findViewById(R.id.recyclerViewCacas);
        fabAgregarCaca = findViewById(R.id.fabAgregarCaca);


        // Por defecto es una lista vacía,
        // se la ponemos al adaptador y configuramos el recyclerView
        listaDeTallas = new ArrayList<>();
        adaptadorTallas = new AdaptadorTallas(listaDeTallas);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adaptadorTallas);

        // Una vez que ya configuramos el RecyclerView le ponemos los datos de la BD
        refrescarListaDeTallas();

        // Listener de los clicks en la lista, o sea el RecyclerView
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override // Un toque sencillo
            public void onClick(View view, int position) {
                // Pasar a la actividad EditarTallaActivity.java
                Talla tallaSeleccionada = listaDeTallas.get(position);
             /*   Intent intent = new Intent(TallasMainActivity.this, EditarTallaActivity.class);
                intent.putExtra("idTalla", tallaSeleccionada.getId());
                intent.putExtra("Peso", tallaSeleccionada.getPeso());
                intent.putExtra("Altura", tallaSeleccionada.getAltura());
                intent.putExtra("comentario", tallaSeleccionada.getComentario());
                startActivity(intent);*/
            }

            @Override // Un toque largo
            public void onLongClick(View view, int position) {
                final Talla tallaParaEliminar = listaDeTallas.get(position);
                AlertDialog dialog = new AlertDialog
                        .Builder(TallasMainActivity.this)
                        .setPositiveButton("Sí, eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tallasController.eliminarTalla(tallaParaEliminar);
                                refrescarListaDeTallas();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setTitle("Confirmar")
                        .setMessage("¿Eliminar Talla del día " + tallaParaEliminar.getPeso() + "?")
                        .create();
                dialog.show();

            }
        }));

        // Listener del FAB
        fabAgregarCaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Simplemente cambiamos de actividad
             //   Intent intent = new Intent(TallasMainActivity.this, AgregarTallaActivity.class);
             //   startActivity(intent);
            }
        });

        // Créditos
        fabAgregarCaca.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(TallasMainActivity.this)
                        .setTitle("Acerca de")
                        .setMessage("Gestor de Babys creado por Mario \n\nIcons made by Freepik from www.flaticon.com ")
                        .setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogo, int which) {
                                dialogo.dismiss();
                            }
                        })
                        .setPositiveButton("Sitio web", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intentNavegador = new Intent(Intent.ACTION_VIEW, Uri.parse("https://parzibyte.me"));
                                startActivity(intentNavegador);
                            }
                        })
                        .create()
                        .show();
                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        refrescarListaDeTallas();
    }

    public void refrescarListaDeTallas() {
        /*
         * ==========
         * Justo aquí obtenemos la lista de la BD
         * y se la ponemos al RecyclerView
         * ============
         *
         * */
        if (adaptadorTallas == null) return;
        listaDeTallas = tallasController.obtenerTallas();
        Collections.reverse(listaDeTallas);
        adaptadorTallas.setListaDeTallas(listaDeTallas);
        adaptadorTallas.notifyDataSetChanged();
    }
}
