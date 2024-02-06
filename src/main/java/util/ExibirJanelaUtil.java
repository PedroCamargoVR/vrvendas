package util;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

public class ExibirJanelaUtil {
    public static void abrirFormulario(JInternalFrame janela, JDesktopPane desktop){
       int lDesk = desktop.getWidth();
       int aDesk = desktop.getHeight();
       int lFrame = janela.getWidth();
       int aFrame = janela.getHeight();
       janela.setLocation(lDesk / 2 - lFrame / 2, aDesk / 2 - aFrame / 2);
       desktop.add(janela);
       janela.setVisible(true);
    }
}
