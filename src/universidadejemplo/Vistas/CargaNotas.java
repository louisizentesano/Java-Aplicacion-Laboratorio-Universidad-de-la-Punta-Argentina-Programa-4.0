package universidadejemplo.Vistas;


/**
 * louisinette
 */
public class CargaNotas extends javax.swing.JInternalFrame {


    public CargaNotas() {
        initComponents();
    
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLCargaNotas = new javax.swing.JLabel();
        jLSelectAlumn = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButtonGuardar = new javax.swing.JButton();
        jButtonSalirCargaNotas = new javax.swing.JButton();
        jComboBListAlumCargaNotas = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableCargaNotas = new javax.swing.JTable();

        setBackground(new java.awt.Color(0, 204, 204));

        jLCargaNotas.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLCargaNotas.setText("Carga de Notas");

        jLSelectAlumn.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        jLSelectAlumn.setText("Seleccione un alumno:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );

        jButtonGuardar.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        jButtonGuardar.setText("Guardar");

        jButtonSalirCargaNotas.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        jButtonSalirCargaNotas.setText("Salir");
        jButtonSalirCargaNotas.setToolTipText("");

        jComboBListAlumCargaNotas.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N

        jTableCargaNotas.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        jTableCargaNotas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTableCargaNotas);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLSelectAlumn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBListAlumCargaNotas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(7, 7, 7))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(175, 175, 175)
                        .addComponent(jLCargaNotas))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(98, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonGuardar)
                .addGap(52, 52, 52)
                .addComponent(jButtonSalirCargaNotas)
                .addGap(108, 108, 108))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(65, 65, 65)
                .addComponent(jLCargaNotas)
                .addGap(96, 96, 96)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLSelectAlumn)
                    .addComponent(jComboBListAlumCargaNotas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(67, 67, 67)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonGuardar)
                    .addComponent(jButtonSalirCargaNotas))
                .addGap(76, 76, 76))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


 
   public static void main(String args[]) {
   }
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButtonGuardar;
    public javax.swing.JButton jButtonSalirCargaNotas;
    public javax.swing.JComboBox jComboBListAlumCargaNotas;
    private javax.swing.JLabel jLCargaNotas;
    private javax.swing.JLabel jLSelectAlumn;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jTableCargaNotas;
    // End of variables declaration//GEN-END:variables

}