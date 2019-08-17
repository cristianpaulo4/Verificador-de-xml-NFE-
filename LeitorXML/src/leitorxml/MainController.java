/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leitorxml;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 *
 * @author Bx
 */
public class MainController implements Initializable {

    @FXML
    private TableView<Produto> tabProd;
    @FXML
    private TableColumn<Produto, String> coluDesc;
    @FXML
    private TableColumn<Produto, Float> coluVUnitario;
    @FXML
    private TableColumn<Produto, Integer> coluQtd;
    @FXML
    private TableColumn<Produto, Float> coluVVenda;
    @FXML
    private TableColumn<Produto, Integer> coluNcm;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       

    }

    @FXML // carregar arquivo xml
    private void carregarXml(MouseEvent event) throws FileNotFoundException, IOException, SAXException, ParserConfigurationException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir Arquivo");
        File arq = fileChooser.showOpenDialog(stage);
        
        ArrayList<Produto> listp = new ArrayList<>();
        
       // ver dados xml      
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document document = documentBuilder.parse(arq);

        NodeList list = document.getElementsByTagName("prod");

        for (int i = 0; i < list.getLength(); i++) {
            org.w3c.dom.Node node = list.item(i);
            if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                Element element = (Element) node;                
                Produto p = new Produto();
                p.setNome(element.getElementsByTagName("xProd").item(0).getTextContent());                
                p.setNCM(Integer.parseInt(element.getElementsByTagName("NCM").item(0).getTextContent())); 
                
               double qtd = Double.parseDouble(element.getElementsByTagName("qCom").item(0).getTextContent());
               DecimalFormat df = new DecimalFormat("##");													
                                          
                                
               p.setQuant(Integer.parseInt(df.format(qtd)));                             
               p.setVUnitario(Float.parseFloat(element.getElementsByTagName("vProd").item(0).getTextContent()));
               p.setVvenda(Float.parseFloat(element.getElementsByTagName("vUnCom").item(0).getTextContent()));                
               listp.add(p);
            }
        }
        
             
        // preencer tabela 
        coluDesc.setCellValueFactory(new PropertyValueFactory<>("nome"));
        coluQtd.setCellValueFactory(new PropertyValueFactory<>("Quant"));
        coluVUnitario.setCellValueFactory(new PropertyValueFactory<>("Vvenda"));
        coluVVenda.setCellValueFactory(new PropertyValueFactory<>("VUnitario"));        
        coluNcm.setCellValueFactory(new PropertyValueFactory<>("NCM"));        
        ObservableList<Produto> listProd = FXCollections.observableArrayList(listp);
        tabProd.setItems(listProd);
               
       
    }

    @FXML
    private void limpar(MouseEvent event) {        
        tabProd.setItems(null);
        
        
    }

}
