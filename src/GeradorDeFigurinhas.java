import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;


public class GeradorDeFigurinhas {

    public void criar(InputStream inputStream, String nomeArquivo, Double estrela) throws Exception{

        // Leitura da imagem

        BufferedImage imagemOriginal = ImageIO.read(inputStream);

        // Padronizar o tamanho
        imagemOriginal= resizeImage(imagemOriginal, 750,1200);

        // Cria nova imagem em memória com transparência e com tamanho novo

        int width = imagemOriginal.getWidth();
        int height = imagemOriginal.getHeight();
        int newHeight = height+200;

        BufferedImage novaImagem = new BufferedImage(width, newHeight, BufferedImage.TRANSLUCENT);

        // Copiar a imagem original para nova imagem

        Graphics2D graphics = (Graphics2D)novaImagem.getGraphics();
        graphics.drawImage(imagemOriginal,0,0, null);


        // Configurar a fonte
        Font fonte = new Font("Impact", Font.ITALIC, 86);


        // Escrever uma frase na nova imagem

        if(estrela > 9) {
            graphics.setFont(fonte);
            graphics.setColor(Color.GREEN.darker());
            graphics.setFont(fonte);
            graphics.drawString("NOTA: "+estrela, 70, newHeight - 100);

            FontRenderContext fontRenderContext = graphics.getFontRenderContext();
            var textLayout = new TextLayout("NOTA: "+estrela, fonte, fontRenderContext);

            Shape outline = textLayout.getOutline(null);
            AffineTransform transform = graphics.getTransform();
            transform.translate(70, newHeight-100);
            graphics.setTransform(transform);

            var outlineStroke = new BasicStroke(60 * 0.054166f);
            graphics.setStroke(outlineStroke);

            graphics.setColor(Color.BLACK);
            graphics.draw(outline);
            graphics.setClip(outline);

        }else{
            //Texto
            graphics.setFont(fonte);
            graphics.setColor(Color.RED.darker());
            graphics.drawString("NOTA: "+estrela, 70, newHeight - 100);
            //Borda
            FontRenderContext fontRenderContext = graphics.getFontRenderContext();
            var textLayout = new TextLayout("NOTA: "+estrela, fonte, fontRenderContext);

            Shape outline = textLayout.getOutline(null);
            AffineTransform transform = graphics.getTransform();
            transform.translate(70, newHeight-100);
            graphics.setTransform(transform);

            var outlineStroke = new BasicStroke(60 * 0.054166f);
            graphics.setStroke(outlineStroke);
            graphics.setColor(Color.BLACK);
            graphics.draw(outline);
            graphics.setClip(outline);
        }

        // Escrever a nova imagem em um arquivo
        File sticker = new File("output/"+nomeArquivo);
        if(sticker.mkdir())
            ImageIO.write(novaImagem, "png", sticker);

    }

    BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws Exception {
        Image imagemFinal = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage ImagemSaida = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        ImagemSaida.getGraphics().drawImage(imagemFinal, 0, 0, null);
        return ImagemSaida;
    }


}