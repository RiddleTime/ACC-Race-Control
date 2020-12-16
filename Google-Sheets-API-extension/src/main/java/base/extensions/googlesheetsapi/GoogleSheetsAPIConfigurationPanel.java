/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.extensions.googlesheetsapi;

/**
 *
 * @author Leonard
 */
public class GoogleSheetsAPIConfigurationPanel extends javax.swing.JPanel {

    /**
     * Creates new form GoogleSheetsAPIConfigurationPanel
     */
    public GoogleSheetsAPIConfigurationPanel() {
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
        java.awt.GridBagConstraints gridBagConstraints;

        enableLabel = new javax.swing.JLabel();
        spreadsheetLinkLabel = new javax.swing.JLabel();
        emptyLabel = new javax.swing.JLabel();
        enabledCheckbox = new javax.swing.JCheckBox();
        spreadSheetLinkTextField = new javax.swing.JTextField();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Google Sheets API"));
        setName("Google Sheets API"); // NOI18N
        java.awt.GridBagLayout layout = new java.awt.GridBagLayout();
        layout.columnWidths = new int[] {100, 100};
        layout.columnWeights = new double[] {0.0, 100.0};
        layout.rowWeights = new double[] {0.0, 0.0, 0.0, 100.0};
        setLayout(layout);

        enableLabel.setText("Enable:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(enableLabel, gridBagConstraints);

        spreadsheetLinkLabel.setText("Spreadsheet Link:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(spreadsheetLinkLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        add(emptyLabel, gridBagConstraints);

        enabledCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enabledCheckboxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        add(enabledCheckbox, gridBagConstraints);

        spreadSheetLinkTextField.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
        add(spreadSheetLinkTextField, gridBagConstraints);

        getAccessibleContext().setAccessibleName("");
    }// </editor-fold>//GEN-END:initComponents

    private void enabledCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enabledCheckboxActionPerformed
        spreadSheetLinkTextField.setEnabled(enabledCheckbox.isSelected());
    }//GEN-LAST:event_enabledCheckboxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel emptyLabel;
    private javax.swing.JLabel enableLabel;
    private javax.swing.JCheckBox enabledCheckbox;
    private javax.swing.JTextField spreadSheetLinkTextField;
    private javax.swing.JLabel spreadsheetLinkLabel;
    // End of variables declaration//GEN-END:variables

    public boolean isExtensionEnabled(){
        return enabledCheckbox.isSelected();
    }
    
    public String getSpreadSheetLink(){
        return spreadSheetLinkTextField.getText();
    }
}