package gm.SistemaBancario.servicio;

import gm.SistemaBancario.dto.TransferenciaDTO;

import javax.swing.text.Document;
import java.io.ByteArrayOutputStream;

public class PDFServicio {

    public byte[] generarPDF(TransferenciaDTO dto) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Comprobante de Transferencia"));
        document.add(new Paragraph("Origen: " + dto.getCuentaOrigen()));
        document.add(new Paragraph("Destino: " + dto.getCuentaDestino()));
        document.add(new Paragraph("Monto: $" + dto.getMonto()));
        document.add(new Paragraph("Motivo: " + dto.getMotivo()));
        document.add(new Paragraph("Fecha: " + dto.getFecha()));
        document.add(new Paragraph("Código: " + dto.getCodigoOperacion()));

        document.close();

        return out.toByteArray();
    }

}
