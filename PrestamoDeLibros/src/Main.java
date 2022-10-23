import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.util.Vector;

//---------------------------------------------------------------------------------------------------------------
class Libros extends javax.swing.JFrame
{
    private JTextField JTtitulo;
    private JTextField JTautor;
    private JTextField JTeditorial;
    private JTextField JTPrestado;

    private JLabel JLtitulo;
    private JLabel JLautor;
    private JLabel JLeditorial;
    private JLabel JLprestado;

    private JMenuBar jMenuBar;
    private JMenu JMarchivo;
    private JMenuItem JMAgregarReg;
    private JMenuItem JMBuscarReg;

    private JSeparator jSeparador;

    public Vector<Libro>coleccionDeLibros;
    private BuscarLibro buscarLibro;
    public Libros()
    {
        initComponents();
        setSize(400,300);
        setTitle("Prestamo de libros");
        coleccionDeLibros=new Vector<Libro>();
    }

    private void initComponents()
    {
        getContentPane().setLayout(null);
        setResizable(false);

        JTtitulo=new JTextField();
        JTautor=new JTextField();
        JTeditorial=new JTextField();
        JTPrestado=new JTextField();

        JLtitulo=new JLabel();
        JLautor=new JLabel();
        JLeditorial=new JLabel();
        JLprestado=new JLabel();

        jMenuBar=new JMenuBar();
        JMarchivo=new JMenu();
        JMAgregarReg =new JMenuItem();
        JMBuscarReg=new JMenuItem();

        jSeparador=new JSeparator();



        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(java.awt.event.WindowEvent evt)
            {
                exitForm(evt);
            }
        });
        JLtitulo.setText("Titulo: ");
        getContentPane().add(JLtitulo);
        JLtitulo.setBounds(20,30,65,16);

        getContentPane().add(JTtitulo);
        JTtitulo.setBounds(95,30,275,24);


        JLautor.setText("Autor: ");
        getContentPane().add(JLautor);
        JLautor.setBounds(20,65,65,16);

        getContentPane().add(JTautor);
        JTautor.setBounds(95,65,275,24);


        JLeditorial.setText("Editorial: ");
        getContentPane().add(JLeditorial);
        JLeditorial.setBounds(20,100,65,16);

        getContentPane().add(JTeditorial);
        JTeditorial.setBounds(95,100,275,24);


        JLprestado.setText("Prestado: ");
        getContentPane().add(JLprestado);
        JLprestado.setBounds(20,140,275,80);

        JTPrestado.setBorder(new javax.swing.border.EtchedBorder());
        getContentPane().add(JTPrestado);
        JTPrestado.setBounds(95,140,275,80);


        setJMenuBar(jMenuBar);

        JMAgregarReg.setText("Agregar registro");
        JMBuscarReg.setText("Buscar registro");

        JMarchivo.setText("Opciones");
        JMarchivo.add(JMAgregarReg);

        JMarchivo.add(JMBuscarReg);
        jMenuBar.add(JMarchivo);

        jMenuBar.add(jSeparador);

        //Manejador de evennto tipo focus para los textFileds

        /*
        JTtitulo.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent evt)
            {
                jtbFocusGained(evt);
            }
        });
        */

        /*
        private void jbtFocusGained(FocusEvent evt)
        {
            JTtitulo.selectAll();
        }
         */

        FocusListener focusListener=new FocusListener() {
            @Override
            public void focusGained(FocusEvent evt) {
                jtbFocusGained(evt);
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        };

        JTtitulo.addFocusListener(focusListener);
        JTautor.addFocusListener(focusListener);
        JTeditorial.addFocusListener(focusListener);
       JTPrestado.addFocusListener(focusListener);

        JMAgregarReg.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                jMenuAgregar(evt);
            }
        });

        JMBuscarReg.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jmItemBuscarLibro(evt);
            }
        });

    }
    private void jmItemBuscarLibro(ActionEvent evt)
    {
        buscarLibro = new BuscarLibro(this, false);
        buscarLibro.setVisible(true);
    }
    private void jtbFocusGained(java.awt.event.FocusEvent evt)
    {
        javax.swing.text.JTextComponent jtb=(javax.swing.text.JTextComponent)evt.getSource();
        jtb.selectAll();
    }

    private void recorrerArrayList(int contador,Libro newLibrO)
    {
        int posiciones=coleccionDeLibros.size()-contador;//Se resta la cantidad de elementos en el arreglo con el contador que nos da la posicion exacta donde va a ir el nuevo libro y nos da el numero de veces que se tiene que recorrer los elementos del arreglo
        for(int i=coleccionDeLibros.size();posiciones>0;i--)//Iniciamos al final del arreglo y paramos cuando el numero de posciones a mover llege a cero
        {
            if(i==coleccionDeLibros.size())//Esto solo va a ocurrir en la primera iteracion
            {
                //Meto un elemento null al arreglo para que no me de index out of range
                Libro Auxiliar=null;
                coleccionDeLibros.add(null);
            }
            //Recorrer un elemento una posicion adelante en el arreglo
            coleccionDeLibros.set(i,coleccionDeLibros.get(i-1));
            posiciones--;
        }
        if(contador==coleccionDeLibros.size())//Esto es cuando el titulo del nuevo libro es el mayor de todos por lo que llega al final del arreglo y simplemente lo metemos al final
        {
            coleccionDeLibros.add(newLibrO);
        }
        else
        {
            coleccionDeLibros.set(contador, newLibrO);//Esto pasa cuando el libro tiene el titulo mas bajo habiendo ya elemento en el arreglo, por lo que contador nos va a dar cero y en esa primera posicion metemos el nuevo libro
        }


    }
    private void jMenuAgregar(ActionEvent evt)
    {
        String mensajeDeConfirmacion="Tu libro "+JTtitulo.getText()+" ha sido agregado.";
        if(JTtitulo.getText().length()==0)//Si no hay titulo en el libro no se puede agregar
        {
            JOptionPane.showMessageDialog(null,"No se puede agregar un libro sin titulo.");
            return;
        }
        else
        {
            Libro newLibrO=new Libro(JTtitulo.getText(),JTautor.getText(),JTeditorial.getText(),JTPrestado.getText());

            if(coleccionDeLibros.size()==0)//Si el arreglo esta vacio , metemos el libro al principio
            {
                coleccionDeLibros.add(newLibrO);

                if(buscarLibro!=null&&buscarLibro.isVisible())//Actualizamos la lista de libros si es que la ventana de la interfaz buscarLibro esta visible
                {
                    buscarLibro.ActualizarLista();
                }
                JOptionPane.showMessageDialog(null,mensajeDeConfirmacion);
            }
            else
            {
                int contador=0;
                while(contador<coleccionDeLibros.size() && newLibrO.getTitulo().compareTo(coleccionDeLibros.get(contador).getTitulo())>0)//Recorre el arreglo y para cuando enecuentra un elemento mayor en el arreglo del que voy a meter o se acaba el arreeglo
                {
                    contador++;
                }

                if (contador==0)//Si contador es igual a cero entonces hay que reocrrer una posicion el arreglo y ,meter el nuevo libro al principio
                {
                    recorrerArrayList(contador,newLibrO);
                    if(buscarLibro!=null&&buscarLibro.isVisible())//Actualizamos la lista de libros si es que la ventana de la interfaz buscarLibro esta visible
                    {
                        buscarLibro.ActualizarLista();
                    }
                    JOptionPane.showMessageDialog(null,mensajeDeConfirmacion);
                }
                else if(newLibrO.getTitulo().compareTo(coleccionDeLibros.get(contador-1).getTitulo())>0)//Se recorre el arreglo y se mete el nuevo libro entre dos elementos del arreglo
                {
                    recorrerArrayList(contador,newLibrO);
                    if(buscarLibro!=null&&buscarLibro.isVisible())//Actualizamos la lista de libros si es que la ventana de la interfaz buscarLibro esta visible
                    {
                        buscarLibro.ActualizarLista();
                    }
                    JOptionPane.showMessageDialog(null,mensajeDeConfirmacion);
                }
                /*
                else
                {
                    coleccionDeLibros.add(newLibrO);//Se terminó el arreglo y se mete el nuevo libro al final del arreglo
                }

                 */
            }
            for(int i=0;i<coleccionDeLibros.size();i++)
            {
                System.out.println();
                System.out.println(coleccionDeLibros.get(i).toString());
            }
        }
    }
    public Vector<Libro> getDatos()
    {
        return coleccionDeLibros;
    }

    private void exitForm(WindowEvent evt)
    {
        System.exit(0);
    }
}
//---------------------------------------------------------------------------------------------------------------
class BuscarLibro extends JDialog
{
    private JScrollPane jScrollPanel;
    private JList jListaLibros;
    private JButton jbtAceptar;
    private JButton jbtCancelar;
    private JButton jbtBorrar;
    private Libros ventanaPadre;
    Vector<Libro>arrayDeLibros;

    public BuscarLibro(java.awt.Frame parent,boolean modal)
    {
        super(parent,modal);
        setSize(300,200);
        initComponents();

        //Acceso a la ventana padre
        ventanaPadre=(Libros)getParent();
        arrayDeLibros=ventanaPadre.getDatos();
        jListaLibros.setListData(ventanaPadre.getDatos());
    }
    private void initComponents()
    {
        jScrollPanel=new JScrollPane();
        jListaLibros =new JList();
        jbtAceptar=new JButton();
        jbtCancelar=new JButton();
        jbtBorrar=new JButton();

        getContentPane().setLayout(null);
        setTitle("Buscar libro prestado");

        addWindowListener(new java.awt.event.WindowAdapter()
        {
            @Override
            public void windowClosed(WindowEvent evt) {
                exitForm(evt);
            }
        });
        jScrollPanel.setViewportView(jListaLibros);
        getContentPane().add(jScrollPanel);
        jScrollPanel.setBounds(10,20,165,130);

        jbtAceptar.setText("Aceptar");
        getRootPane().setDefaultButton(jbtAceptar);
        getContentPane().add(jbtAceptar);
        jbtAceptar.setBounds(190,20,90,25);

        jbtCancelar.setText("Cancelar");
        getContentPane().add(jbtCancelar);
        jbtCancelar.setBounds(190,55,90,25);

        jbtBorrar.setText("Borrar");
        getContentPane().add(jbtBorrar);
        jbtBorrar.setBounds(190,90,90,25);

        jbtAceptar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                selectedIndex(evt);

            }
        });

        jbtBorrar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                borrarElemento(evt);
            }
        });

        jbtCancelar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                setVisible(false);
                dispose();
            }
        });
    }
    public void ActualizarLista()
    {
        jListaLibros.setListData(ventanaPadre.getDatos());
    }
    private void selectedIndex(ActionEvent evt)
    {
        int i=jListaLibros.getSelectedIndex();

        ListarLibro listarLibro=new ListarLibro(i,arrayDeLibros.get(i));
        listarLibro.setVisible(true);
    }

    private void borrarElemento(ActionEvent evt)
    {
        int i=jListaLibros.getSelectedIndex();
        JOptionPane.showMessageDialog(null,"El libro: "+arrayDeLibros.get(i).getTitulo()+" ha sido borrado.");
        arrayDeLibros.remove(i);
        ActualizarLista();

    }

    private void exitForm(WindowEvent evt)
    {
        setVisible(false);
        dispose();
    }

}
//---------------------------------------------------------------------------------------------------------------
class ListarLibro extends JDialog
{
    private JLabel JLtitulo;
    private JLabel JLautor;
    private JLabel JLeditorial;
    private JLabel JLprestado;

    private JTextField JTtitulo;
    private JTextField JTautor;
    private JTextField JTeditorial;
    private JTextField JTPrestado;
    private Libro miLibro;


    public ListarLibro(int index,Libro miLibro)
    {
        this.miLibro=miLibro;
        initComponents();
        setSize(400,300);
        setTitle("Libro: "+miLibro.getTitulo());
    }

    private void initComponents() {
        getContentPane().setLayout(null);
        setResizable(false);

        JLtitulo = new JLabel();
        JLautor = new JLabel();
        JLeditorial = new JLabel();
        JLprestado = new JLabel();

        JTtitulo=new JTextField();
        JTautor=new JTextField();
        JTeditorial=new JTextField();
        JTPrestado=new JTextField();


        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(java.awt.event.WindowEvent evt)
            {
                exitForm(evt);
            }
        });

        JLtitulo.setText("Titulo: ");
        getContentPane().add(JLtitulo);
        JLtitulo.setBounds(20,30,65,16);

        getContentPane().add(JTtitulo);
        JTtitulo.setBounds(95,30,275,24);
        JTtitulo.setText(miLibro.getTitulo());
        JTtitulo.setEditable(false);



        JLautor.setText("Autor: ");
        getContentPane().add(JLautor);
        JLautor.setBounds(20,65,65,16);

        getContentPane().add(JTautor);
        JTautor.setBounds(95,65,275,24);
        JTautor.setText(miLibro.getAutor());
        JTautor.setEditable(false);



        JLeditorial.setText("Editorial: ");
        getContentPane().add(JLeditorial);
        JLeditorial.setBounds(20,100,65,16);

        getContentPane().add(JTeditorial);
        JTeditorial.setBounds(95,100,275,24);
        JTeditorial.setText(miLibro.getEditorial());
        JTeditorial.setEditable(false);



        JLprestado.setText("Prestado: ");
        getContentPane().add(JLprestado);
        JLprestado.setBounds(20,140,275,80);

        getContentPane().add(JTPrestado);
        JTPrestado.setBounds(95,140,275,80);
        JTPrestado.setText(miLibro.getPrestado());
        JTPrestado.setEditable(false);



    }
    private void exitForm(WindowEvent evt)
    {
        setVisible(false);
        dispose();
    }
}

//---------------------------------------------------------------------------------------------------------------
class Libro
{
    private String titulo;
    private String autor;
    private String editorial;
    private String prestado;

    public Libro(String titulo, String autor, String editorial, String prestado)
    {
        this.titulo=titulo;
        this.autor=autor;
        this.editorial=editorial;
        this.prestado=prestado;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public String getPrestado() {
        return prestado;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public void setPrestado(String prestado) {
        this.prestado = prestado;
    }

    @Override
    public String toString() {
        return titulo;
    }

}





//---------------------------------------------------------------------------------------------------------------
public class Main
{
    public static void main(String[] args)
    {
        try
        {
            //javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getCrossPlatformLookAndFeelClassName());
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception ex)
        {
            System.out.println("No es posible establecer el aspecto deseado "+ex);
        }
        new Libros().setVisible(true);

    }
}