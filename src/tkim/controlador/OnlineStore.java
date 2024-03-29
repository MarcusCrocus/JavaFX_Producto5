package tkim.controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;

import tkim.modelo.Articulo;
import tkim.modelo.Cliente;
import tkim.modelo.Exceptions;
import tkim.modelo.Pedido;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swt.FXCanvas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class OnlineStore extends Application implements Initializable {

	Scanner teclado = new Scanner(System.in);
	Controlador contro = new Controlador();

	@FXML
	private ImageView tkimImageView;
	@FXML
	private Image myImage = new Image(getClass().getResourceAsStream("tkim.jpg"));

	
	// ##############################FORMULARIO INSERTAR CLIENTES##############################
	@FXML
	private Label lblNif, lblNombre, lblDomicilio, lblEmail, lblCuota, lblDescuento;
	@FXML
	private TextField nif, nombre, domicilio, email,cuota_anual, descuento_envio;
	
	@FXML
	private ComboBox<String> lblTipoCliente = new ComboBox<String>();
	

	// ##############################FORMULARIO INSERTAR ARTICULOS##############################
	@FXML
	private TextField codigoArticulo, descripcion, precioVenta, gastosEnvio, tiempoPreparacion;
	@FXML
	private Label lblCodigoArticulo, lblPrecioVenta, lblGastosEnvio, lblTiempoPreparacion;

	@FXML
	private Button boton;
	// #########################################################################################

	// ##############################FORMULARIO INSERTAR
	// PEDIDOS################################
	@FXML
	private TextField codigoPedido, unidadesPedido;
	@FXML
	private ComboBox<Cliente> cbClientes = new ComboBox<>();
	@FXML
	private ComboBox<Articulo> cbArticulos = new ComboBox<>();
	@FXML
	private Label lblErrorCodigoPedido, lblErrorUnidadesPedido, labelNif, labelNombre, labelDomicilio,
				  labelEmail, labelTC, labelCA, labelDE, labelCodigoArticulo, labelDescripcion, labelPV, 
				  labelGE, labelTP;
	// #########################################################################################

	@Override
	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("../vista/onlineStore.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Online Store");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) throws Exception {
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		launch(args);
	}

	public void handleButtonPress(ActionEvent event) throws IOException {
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		String nameButton = ((Button) event.getSource()).getId();
		switch (nameButton) {
		case "btnCrearCliente":
			try {

				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../vista/insertarCliente.fxml"));
				Parent root1 = (Parent) fxmlLoader.load();
				Stage stage = new Stage();
				stage.setScene(new Scene(root1));
				stage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "buttonInsertArticle":
			try {

				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../vista/insertarArticulo.fxml"));
				Parent root1 = (Parent) fxmlLoader.load();
				Stage stage = new Stage();
				stage.setScene(new Scene(root1));
				stage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "buttonInsertPedido":
			try {

				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../vista/insertarPedido.fxml"));
				Parent root1 = (Parent) fxmlLoader.load();
				Stage stage = new Stage();
				stage.setScene(new Scene(root1));
				stage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "buttonShowArticle":
			BorderPane borderPane = new BorderPane();
			List<Articulo> articulos = contro.mostrarArticulos();

			// El String que ponemos entre parentesis en setCellValueFactory es el nombre de
			// la variable de clase de Articulo
			TableView<Articulo> tvArticulos = new TableView<Articulo>();
			TableColumn<Articulo, String> codigoArticuloColumn = new TableColumn<>("Codigo de artículo");
			codigoArticuloColumn.setCellValueFactory(new PropertyValueFactory<Articulo, String>("codigo"));
			TableColumn<Articulo, String> descripcionColumn = new TableColumn<>("Descripción");
			descripcionColumn.setCellValueFactory(new PropertyValueFactory<Articulo, String>("descripcion"));
			TableColumn<Articulo, String> precioVentaColumn = new TableColumn<>("Precio de venta");
			precioVentaColumn.setCellValueFactory(new PropertyValueFactory<Articulo, String>("precioVenta"));
			TableColumn<Articulo, String> gastosEnvioColumn = new TableColumn<>("Gastos de envío");
			gastosEnvioColumn.setCellValueFactory(new PropertyValueFactory<Articulo, String>("gastosEnvio"));
			TableColumn<Articulo, String> tiempoPreparacionColumn = new TableColumn<>("Tiempo de preparación");
			tiempoPreparacionColumn
					.setCellValueFactory(new PropertyValueFactory<Articulo, String>("tiempoPreparacion"));

			tvArticulos.getColumns().add(codigoArticuloColumn);
			tvArticulos.getColumns().add(descripcionColumn);
			tvArticulos.getColumns().add(precioVentaColumn);
			tvArticulos.getColumns().add(gastosEnvioColumn);
			tvArticulos.getColumns().add(tiempoPreparacionColumn);
			tvArticulos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

			for (Articulo articulo : articulos) {
				tvArticulos.getItems().add(articulo);
			}
			borderPane.setCenter(tvArticulos);

			Scene scene = new Scene(borderPane, 690, 300);
			Stage stage = new Stage();
			String styleArticulos = getClass().getResource("style.css").toExternalForm();
			scene.getStylesheets().add(styleArticulos);
			stage.setTitle("ARTICULOS");
			stage.setScene(scene);
			stage.show();

			break;
		case "buttonShowClientsAll":
			BorderPane borderPaneClientes = new BorderPane();
			List<Cliente> clientes = contro.mostrarClientesTodos();

			// El String que ponemos entre parentesis en setCellValueFactory es el nombre de
			// la variable de clase de Cliente
			TableView<Cliente> tvClientes = new TableView<Cliente>();
			TableColumn<Cliente, String> nifColumn = new TableColumn<>("Nif");
			nifColumn.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nif"));
			TableColumn<Cliente, String> nombreColumn = new TableColumn<>("Nombre");
			nombreColumn.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombre"));
			TableColumn<Cliente, String> domicilioColumn = new TableColumn<>("Domicilio");
			domicilioColumn.setCellValueFactory(new PropertyValueFactory<Cliente, String>("domicilio"));
			TableColumn<Cliente, String> emailColumn = new TableColumn<>("Email");
			emailColumn.setCellValueFactory(new PropertyValueFactory<Cliente, String>("email"));
			TableColumn<Cliente, String> tipoClienteColumn = new TableColumn<>("Tipo de cliente");
			tipoClienteColumn.setCellValueFactory(new PropertyValueFactory<Cliente, String>("tipo_cliente"));
			TableColumn<Cliente, String> cuotaAnualColumn = new TableColumn<>("Cuota anual");
			cuotaAnualColumn.setCellValueFactory(new PropertyValueFactory<Cliente, String>("cuota_anual"));
			TableColumn<Cliente, String> descuentoEnvioColum = new TableColumn<>("Descuento en el envío");
			descuentoEnvioColum.setCellValueFactory(new PropertyValueFactory<Cliente, String>("descuento_envio"));

			tvClientes.getColumns().add(nifColumn);
			tvClientes.getColumns().add(nombreColumn);
			tvClientes.getColumns().add(domicilioColumn);
			tvClientes.getColumns().add(emailColumn);
			tvClientes.getColumns().add(tipoClienteColumn);
			tvClientes.getColumns().add(cuotaAnualColumn);
			tvClientes.getColumns().add(descuentoEnvioColum);
			tvClientes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

			for (Cliente cliente : clientes) {
				tvClientes.getItems().add(cliente);
			}
			borderPaneClientes.setCenter(tvClientes);

			Scene sceneClientesTodos = new Scene(borderPaneClientes, 900, 300);
			Stage stageClientesTodos = new Stage();
			String styleClientesTodos = getClass().getResource("style.css").toExternalForm();
			sceneClientesTodos.getStylesheets().add(styleClientesTodos);
			stageClientesTodos.setTitle("CLIENTES");
			stageClientesTodos.setScene(sceneClientesTodos);
			stageClientesTodos.show();
			break;
		case "buttonShowStandardClients":
			BorderPane borderPaneClientesEstandar = new BorderPane();
			List<Cliente> clientesEstandar = contro.mostrarClientesEstandar();

			// El String que ponemos entre parentesis en setCellValueFactory es el nombre de
			// la variable de clase de Cliente
			TableView<Cliente> tvClientesEstandar = new TableView<Cliente>();
			TableColumn<Cliente, String> nifCEColumn = new TableColumn<>("Nif");
			nifCEColumn.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nif"));
			TableColumn<Cliente, String> nombreCEColumn = new TableColumn<>("Nombre");
			nombreCEColumn.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombre"));
			TableColumn<Cliente, String> domicilioCEColumn = new TableColumn<>("Domicilio");
			domicilioCEColumn.setCellValueFactory(new PropertyValueFactory<Cliente, String>("domicilio"));
			TableColumn<Cliente, String> emailCEColumn = new TableColumn<>("Email");
			emailCEColumn.setCellValueFactory(new PropertyValueFactory<Cliente, String>("email"));
			TableColumn<Cliente, String> tipoClienteCEColumn = new TableColumn<>("Tipo de cliente");
			tipoClienteCEColumn.setCellValueFactory(new PropertyValueFactory<Cliente, String>("tipo_cliente"));
			TableColumn<Cliente, String> cuotaAnualCEColumn = new TableColumn<>("Cuota anual");
			cuotaAnualCEColumn.setCellValueFactory(new PropertyValueFactory<Cliente, String>("cuota_anual"));
			TableColumn<Cliente, String> descuentoCEEnvioColum = new TableColumn<>("Descuento en el envío");
			descuentoCEEnvioColum.setCellValueFactory(new PropertyValueFactory<Cliente, String>("descuento_envio"));

			tvClientesEstandar.getColumns().add(nifCEColumn);
			tvClientesEstandar.getColumns().add(nombreCEColumn);
			tvClientesEstandar.getColumns().add(domicilioCEColumn);
			tvClientesEstandar.getColumns().add(emailCEColumn);
			tvClientesEstandar.getColumns().add(tipoClienteCEColumn);
			tvClientesEstandar.getColumns().add(cuotaAnualCEColumn);
			tvClientesEstandar.getColumns().add(descuentoCEEnvioColum);
			tvClientesEstandar.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

			for (Cliente clienteEstandar : clientesEstandar) {
				tvClientesEstandar.getItems().add(clienteEstandar);
			}
			borderPaneClientesEstandar.setCenter(tvClientesEstandar);

			Scene sceneClientesEstandar = new Scene(borderPaneClientesEstandar);
			Stage stageClientesEstandar = new Stage();
			String styleClientesEstandar = getClass().getResource("style.css").toExternalForm();
			sceneClientesEstandar.getStylesheets().add(styleClientesEstandar);
			stageClientesEstandar.setTitle("CLIENTES ESTANDAR");
			stageClientesEstandar.setScene(sceneClientesEstandar);
			stageClientesEstandar.show();
			break;
		case "buttonShowPremiumClients":
			BorderPane borderPaneClientesPremium = new BorderPane();

			// El String que ponemos entre parentesis en setCellValueFactory es el nombre de
			// la variable de clase de Cliente
			TableView<Cliente> tvClientesPremium = new TableView<Cliente>();
			TableColumn<Cliente, String> nifCPColumn = new TableColumn<>("Nif");
			nifCPColumn.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nif"));
			TableColumn<Cliente, String> nombreCPColumn = new TableColumn<>("Nombre");
			nombreCPColumn.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombre"));
			TableColumn<Cliente, String> domicilioCPColumn = new TableColumn<>("Domicilio");
			domicilioCPColumn.setCellValueFactory(new PropertyValueFactory<Cliente, String>("domicilio"));
			TableColumn<Cliente, String> emailCPColumn = new TableColumn<>("Email");
			emailCPColumn.setCellValueFactory(new PropertyValueFactory<Cliente, String>("email"));
			TableColumn<Cliente, String> tipoClienteCPColumn = new TableColumn<>("Tipo de cliente");
			tipoClienteCPColumn.setCellValueFactory(new PropertyValueFactory<Cliente, String>("tipo_cliente"));
			TableColumn<Cliente, String> cuotaAnualCPColumn = new TableColumn<>("Cuota anual");
			cuotaAnualCPColumn.setCellValueFactory(new PropertyValueFactory<Cliente, String>("cuota_anual"));
			TableColumn<Cliente, String> descuentoCPEnvioColum = new TableColumn<>("Descuento envío");
			descuentoCPEnvioColum.setCellValueFactory(new PropertyValueFactory<Cliente, String>("descuento_envio"));

			tvClientesPremium.getColumns().add(nifCPColumn);
			tvClientesPremium.getColumns().add(nombreCPColumn);
			tvClientesPremium.getColumns().add(domicilioCPColumn);
			tvClientesPremium.getColumns().add(emailCPColumn);
			tvClientesPremium.getColumns().add(tipoClienteCPColumn);
			tvClientesPremium.getColumns().add(cuotaAnualCPColumn);
			tvClientesPremium.getColumns().add(descuentoCPEnvioColum);
			tvClientesPremium.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

			List<Cliente> clientesPremium = contro.mostrarClientesPremium();
			ObservableList<Cliente> listClientesPremium = FXCollections.observableArrayList();
			listClientesPremium.addAll(clientesPremium);

			tvClientesPremium.setItems(listClientesPremium);
			borderPaneClientesPremium.setCenter(tvClientesPremium);
			Scene sceneClientesPremium = new Scene(borderPaneClientesPremium);

			Stage stageClientesPremium = new Stage();
			String styleClientesPremium = getClass().getResource("style.css").toExternalForm();
			sceneClientesPremium.getStylesheets().add(styleClientesPremium);
			stageClientesPremium.setTitle("CLIENTES PREMIUM");
			stageClientesPremium.setScene(sceneClientesPremium);
			stageClientesPremium.show();
			break;
		case "buttonClientesPedidos":
			try {
				// El String que ponemos entre parentesis en setCellValueFactory es el nombre de
				// la variable de clase de Cliente
				BorderPane borderPanePedidosXcliente = new BorderPane();
				
				TableView<Cliente> tvPedidosPendientescliente = new TableView<Cliente>();
				TableColumn<Cliente, String> pendientesNifCliente = new TableColumn<>("Nif");
				pendientesNifCliente.setCellValueFactory(new PropertyValueFactory<>("nif"));
				TableColumn<Cliente, String> pendientesNombreCliente = new TableColumn<>("Nombre");
				pendientesNombreCliente.setCellValueFactory(new PropertyValueFactory<>("nombre"));
				TableColumn<Cliente, String> pendientesDomicilioCliente = new TableColumn<>("Domicilio");
				pendientesDomicilioCliente.setCellValueFactory(new PropertyValueFactory<>("domicilio"));
				TableColumn<Cliente, String> pedidosEmailCliente = new TableColumn<>("Email");
				pedidosEmailCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("email"));
				TableColumn<Cliente, String> pedidosTipoCliente = new TableColumn<>("Tipo de cliente");
				pedidosTipoCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("tipo_cliente"));
				TableColumn<Cliente, String> pedidosCuotaAnualCliente = new TableColumn<>("Cuota anual");
				pedidosCuotaAnualCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("cuota_anual"));
				TableColumn<Cliente, String> pedidosDescuentoEnvioCliente = new TableColumn<>("Descuento en el envío");
				pedidosDescuentoEnvioCliente
						.setCellValueFactory(new PropertyValueFactory<Cliente, String>("descuento_envio"));
				TableColumn<Cliente, String> pendientesButton = new TableColumn<>("Pedidos pendientes");
				TableColumn<Cliente, String> enviadosButton = new TableColumn<>("Pedidos enviados");

				tvPedidosPendientescliente.getColumns().add(pendientesNifCliente);
				tvPedidosPendientescliente.getColumns().add(pendientesNombreCliente);
				tvPedidosPendientescliente.getColumns().add(pendientesDomicilioCliente);
				tvPedidosPendientescliente.getColumns().add(pedidosEmailCliente);
				tvPedidosPendientescliente.getColumns().add(pedidosTipoCliente);
				tvPedidosPendientescliente.getColumns().add(pedidosCuotaAnualCliente);
				tvPedidosPendientescliente.getColumns().add(pedidosDescuentoEnvioCliente);
				tvPedidosPendientescliente.getColumns().add(pendientesButton);
				tvPedidosPendientescliente.getColumns().add(enviadosButton);
				tvPedidosPendientescliente.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

				Callback<TableColumn<Cliente, String>, TableCell<Cliente, String>> cellFactoryPendientes = (param) -> {
					final TableCell<Cliente, String> cell = new TableCell<Cliente, String>() {
						@Override
						public void updateItem(String item, boolean empty) {
							super.updateItem(item, empty);
							if (empty) {
								setGraphic(null);
								setText(null);
							} else {
								final Button pendientesButton = new Button("Ver");
								//pendientesButton.getStylesheets().add("src/controlador/style.css");
								pendientesButton.setOnAction(event -> {
									Cliente c = getTableView().getItems().get(getIndex());
									botonPendientes(c);
								});
								setGraphic(pendientesButton);
								setText(null);
							}
						}
					};
					return cell;
				};

				Callback<TableColumn<Cliente, String>, TableCell<Cliente, String>> cellFactoryEnviados = (param) -> {
					final TableCell<Cliente, String> cell = new TableCell<Cliente, String>() {
						@Override
						public void updateItem(String item, boolean empty) {
							super.updateItem(item, empty);
							if (empty) {
								setGraphic(null);
								setText(null);
							} else {
								final Button enviadosButton = new Button("Ver");
								enviadosButton.setOnAction(event -> {
									Cliente c = getTableView().getItems().get(getIndex());
									botonEnviados(c);
								});
								setGraphic(enviadosButton);
								setText(null);
							}
						}
					};
					return cell;
				};
				List<Cliente> cPremium = contro.mostrarClientesTodos();
				ObservableList<Cliente> listPedidosXcliente = FXCollections.observableArrayList();
				listPedidosXcliente.addAll(cPremium);
				pendientesButton.setCellFactory(cellFactoryPendientes);
				enviadosButton.setCellFactory(cellFactoryEnviados);
				tvPedidosPendientescliente.setItems(listPedidosXcliente);
				borderPanePedidosXcliente.setCenter(tvPedidosPendientescliente);
				Scene sceneClientesT = new Scene(borderPanePedidosXcliente,1250,300);
				Stage stageClientesT = new Stage();
				String styleClientesPedidos = getClass().getResource("style.css").toExternalForm();
				sceneClientesT.getStylesheets().add(styleClientesPedidos);
				stageClientesT.setTitle("ESCOGE EL CLIENTE");
				stageClientesT.setScene(sceneClientesT);
				stageClientesT.show();

			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "showPedidos":
			mostrarPedidos();
			break;
		default:
			break;
		}

	}

	public void mostrarPedidos() {
		try {
			// El String que ponemos entre parentesis en setCellValueFactory es el nombre de
			// la variable de clase de Cliente
			BorderPane borderPanePedidos = new BorderPane();
			TableView<Pedido> tvPedidosPendientescliente = new TableView<Pedido>();
			TableColumn<Pedido, Integer> codigoPedido = new TableColumn<>("Código Pedido");
			codigoPedido.setCellValueFactory(new PropertyValueFactory<>("numero_pedido"));
			TableColumn<Pedido, Integer> unidadesPedido = new TableColumn<>("Unidades");
			unidadesPedido.setCellValueFactory(new PropertyValueFactory<>("unidadesPedido"));
			TableColumn<Pedido, Date> fechaHoraPedido = new TableColumn<>("Fecha del pedido");
			fechaHoraPedido.setCellValueFactory(new PropertyValueFactory<>("fechaHoraPedido"));
			TableColumn<Pedido, Integer> totalPedido = new TableColumn<>("Total del pedido");
			totalPedido.setCellValueFactory(new PropertyValueFactory<>("totalPedido"));
			TableColumn<Pedido, String> clientePedido = new TableColumn<>("Cliente del pedido");
			clientePedido.setCellValueFactory(new PropertyValueFactory<>("cliente"));
			TableColumn<Pedido, String> articuloPedido = new TableColumn<>("Artículo del pedido");
			articuloPedido.setCellValueFactory(new PropertyValueFactory<>("articulo"));
			TableColumn<Pedido, String> eliminarPedidoButton = new TableColumn<>("Eliminar pedido");

			tvPedidosPendientescliente.getColumns().add(codigoPedido);
			tvPedidosPendientescliente.getColumns().add(unidadesPedido);
			tvPedidosPendientescliente.getColumns().add(fechaHoraPedido);
			tvPedidosPendientescliente.getColumns().add(totalPedido);
			tvPedidosPendientescliente.getColumns().add(clientePedido);
			tvPedidosPendientescliente.getColumns().add(articuloPedido);
			tvPedidosPendientescliente.getColumns().add(eliminarPedidoButton);
			tvPedidosPendientescliente.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			List<Pedido> pedidos = contro.mostrarPedidos();
			ObservableList<Pedido> listPedidosXcliente = FXCollections.observableArrayList();
			listPedidosXcliente.addAll(pedidos);

			tvPedidosPendientescliente.setItems(listPedidosXcliente);
			borderPanePedidos.setCenter(tvPedidosPendientescliente);
			Scene sceneClientesT = new Scene(borderPanePedidos);
			Stage stageClientesT = new Stage();
			String  style= getClass().getResource("style.css").toExternalForm();
			sceneClientesT.getStylesheets().add(style);
			stageClientesT.setTitle("ESCOGE EL CLIENTE");
			stageClientesT.setScene(sceneClientesT);

			Callback<TableColumn<Pedido, String>, TableCell<Pedido, String>> cellFactoryPendientes = (param) -> {
				final TableCell<Pedido, String> cell = new TableCell<Pedido, String>() {
					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							final Button pendientesButton = new Button("Eliminar");
							pendientesButton.setOnAction(event -> {
								Pedido c = getTableView().getItems().get(getIndex());
								String eliminar = contro.eliminarPedido(c.getNumero_pedido());
								Alert mensajeConfirmacionModal = new Alert(AlertType.INFORMATION);
								mensajeConfirmacionModal.setTitle("PEDIDOS");
								mensajeConfirmacionModal.setHeaderText(null);
								mensajeConfirmacionModal.setContentText(eliminar);
								mensajeConfirmacionModal.showAndWait();
								if (eliminar.equals("El pedido se ha borrado correctamente")) {
									stageClientesT.close();
									mostrarPedidos();
								}
							});
							setGraphic(pendientesButton);
							setText(null);
						}
					}
				};
				return cell;
			};

			eliminarPedidoButton.setCellFactory(cellFactoryPendientes);
			stageClientesT.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void botonEnviados(Cliente c) {
		BorderPane borderPanePedidos = new BorderPane();
        TableView<Pedido> tvPedidosPendientescliente = new TableView<Pedido>();
        TableColumn<Pedido, Integer> codigoPedido = new TableColumn<>("Código Pedido");
        codigoPedido.setCellValueFactory(new PropertyValueFactory<>("numero_pedido"));
        TableColumn<Pedido, Integer> unidadesPedido = new TableColumn<>("Unidades");
        unidadesPedido.setCellValueFactory(new PropertyValueFactory<>("unidadesPedido"));
        TableColumn<Pedido, Date> fechaHoraPedido = new TableColumn<>("Fecha del pedido");
        fechaHoraPedido.setCellValueFactory(new PropertyValueFactory<>("fechaHoraPedido"));
        TableColumn<Pedido, Integer> totalPedido = new TableColumn<>("Total del pedido");
        totalPedido.setCellValueFactory(new PropertyValueFactory<>("totalPedido"));
        TableColumn<Pedido, String> articuloPedido = new TableColumn<>("Artículo del pedido");
        articuloPedido.setCellValueFactory(new PropertyValueFactory<>("articulo"));
        TableColumn<Pedido, String> verArticulo = new TableColumn<>("Mostrar");
        tvPedidosPendientescliente.getColumns().add(codigoPedido);
        tvPedidosPendientescliente.getColumns().add(unidadesPedido);
        tvPedidosPendientescliente.getColumns().add(fechaHoraPedido);
        tvPedidosPendientescliente.getColumns().add(totalPedido);
        tvPedidosPendientescliente.getColumns().add(articuloPedido);
        tvPedidosPendientescliente.getColumns().add(verArticulo);
        
        Callback<TableColumn<Pedido, String>, TableCell<Pedido, String>> cellFactoryPendientes = (param) -> {
			final TableCell<Pedido, String> cell = new TableCell<Pedido, String>() {
				@Override
				public void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setGraphic(null);
						setText(null);
					} else {
						final Button articuloPedido = new Button("Artículo del pedido");
						articuloPedido.setOnAction(event -> {
							Pedido p = getTableView().getItems().get(getIndex());
							mostrarArticuloPedido(p);
						});
						setGraphic(articuloPedido);
						setText(null);
					}
				}
			};
			return cell;
		};
		verArticulo.setCellFactory(cellFactoryPendientes);
        tvPedidosPendientescliente.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        List<Pedido> pedidos = contro.mostrarPedEnviados(c.getNif());
        ObservableList<Pedido> listPedidosXcliente = FXCollections.observableArrayList();
        listPedidosXcliente.addAll(pedidos);
        tvPedidosPendientescliente.setItems(listPedidosXcliente);
        borderPanePedidos.setCenter(tvPedidosPendientescliente);
        Scene sceneClientesT = new Scene(borderPanePedidos);
        Stage stageClientesT = new Stage();
        String  style= getClass().getResource("style.css").toExternalForm();
        sceneClientesT.getStylesheets().add(style);
        stageClientesT.setTitle("PEDIDOS ENVIADOS DE " + c.getNombre() + " con NIF " + c.getNif());
        stageClientesT.setScene(sceneClientesT);
        stageClientesT.show();
	}

	public void botonPendientes(Cliente c) {
		BorderPane borderPanePedidos = new BorderPane();
		
		TableView<Pedido> tvPedidosPendientescliente = new TableView<Pedido>();
		TableColumn<Pedido, Integer> codigoPedido = new TableColumn<>("Código Pedido");
		codigoPedido.setCellValueFactory(new PropertyValueFactory<>("numero_pedido"));
		TableColumn<Pedido, Integer> unidadesPedido = new TableColumn<>("Unidades");
		unidadesPedido.setCellValueFactory(new PropertyValueFactory<>("unidadesPedido"));
		TableColumn<Pedido, Date> fechaHoraPedido = new TableColumn<>("Fecha del pedido");
		fechaHoraPedido.setCellValueFactory(new PropertyValueFactory<>("fechaHoraPedido"));
		TableColumn<Pedido, Integer> totalPedido = new TableColumn<>("Total del pedido");
		totalPedido.setCellValueFactory(new PropertyValueFactory<>("totalPedido"));
		TableColumn<Pedido, String> clientePedido = new TableColumn<>("Cliente del pedido");
		clientePedido.setCellValueFactory(new PropertyValueFactory<>("cliente"));
		TableColumn<Pedido, String> articuloPedido = new TableColumn<>("Artículo del pedido");
		articuloPedido.setCellValueFactory(new PropertyValueFactory<>("articulo"));
		TableColumn<Pedido, String> verArticulo = new TableColumn<>("Mostrar");
		/*verArticulo.setCellValueFactory(new Callback<CellDataFeatures<Pedido, String>, ObservableValue<String>>() {
		     public ObservableValue<String> call(CellDataFeatures<Pedido, String> p) {
		         // p.getValue() returns the Person instance for a particular TableView row
		    	 Articulo a = contro.buscarArticulo(p.getValue().getArticulo());
		         return a.getDescripcion();
		     }
		  });*/
		
		
		Callback<TableColumn<Pedido, String>, TableCell<Pedido, String>> cellFactoryPendientes = (param) -> {
			final TableCell<Pedido, String> cell = new TableCell<Pedido, String>() {
				@Override
				public void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setGraphic(null);
						setText(null);
					} else {
						final Button articuloPedido = new Button("Artículo del pedido");
						articuloPedido.setOnAction(event -> {
							Pedido p = getTableView().getItems().get(getIndex());
							mostrarArticuloPedido(p);
						});
						setGraphic(articuloPedido);
						setText(null);
					}
				}
			};
			return cell;
		};
		verArticulo.setCellFactory(cellFactoryPendientes);
		tvPedidosPendientescliente.getColumns().add(codigoPedido);
		tvPedidosPendientescliente.getColumns().add(unidadesPedido);
		tvPedidosPendientescliente.getColumns().add(fechaHoraPedido);
		tvPedidosPendientescliente.getColumns().add(totalPedido);
		tvPedidosPendientescliente.getColumns().add(clientePedido);
		tvPedidosPendientescliente.getColumns().add(articuloPedido);
		tvPedidosPendientescliente.getColumns().add(verArticulo);
		tvPedidosPendientescliente.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		List<Pedido> pedidosPendientes = contro.mostrarPedPendientes(c.getNif());
		ObservableList<Pedido> listPedidosXcliente = FXCollections.observableArrayList();
		listPedidosXcliente.addAll(pedidosPendientes);
		
		tvPedidosPendientescliente.setItems(listPedidosXcliente);
		borderPanePedidos.setCenter(tvPedidosPendientescliente);
		Scene sceneClientesT = new Scene(borderPanePedidos,1000,300);
		Stage stageClientesT = new Stage();
		String  style= getClass().getResource("style.css").toExternalForm();
		sceneClientesT.getStylesheets().add(style);
		stageClientesT.setTitle("PEDIDOS PENDIENTES DE " + c.getNombre() + " con NIF " + c.getNif());
		stageClientesT.setScene(sceneClientesT);
		stageClientesT.show();
	}

	public void mostrarArticuloPedido(Pedido p) {
		Articulo a = contro.buscarArticulo(p.getArticulo());

		
		Alert mensajeConfirmacionModal = new Alert(AlertType.INFORMATION);
		mensajeConfirmacionModal.setTitle("ARTÍCULO DEL PEDIDO");
		mensajeConfirmacionModal.setHeaderText(null);
		mensajeConfirmacionModal.setContentText("Código artículo: " + a.getCodigo() + 
												"\nDescripcion: " +a.getDescripcion() +
												"\nPrecio de venta: " + a.getPrecioVenta() +
												"\nGastos de envío: " + a.getGastosEnvio() +
												"\nTiempo de preparación: " + a.getTiempoPreparacion());
		mensajeConfirmacionModal.showAndWait();
	}
	
	public void submit(ActionEvent event) {
		String botonClicado = boton.getText();
		switch (botonClicado) {
		case "Insertar articulo":
			if (!codigoArticulo.getText().equals("") && !descripcion.getText().equals("") && !precioVenta.getText().equals("") 
					&& !gastosEnvio.getText().equals("") && !tiempoPreparacion.getText().equals("")) {
			Boolean tiempoInferior = true;
			Boolean existeArticulo = contro.existeArticulo(codigoArticulo.getText());
			Boolean esPrecioVentaFloat = esFloat(precioVenta.getText()); // true es correcto
			Boolean esGastosEnvioFloat = esFloat(gastosEnvio.getText()); // true es correcto
			
			if (Integer.parseInt(tiempoPreparacion.getText()) < 120) {
				try {
					tiempoInferior = false;
					throw new Exceptions("El tiempo de preparación no puede ser inferior a 120min");
				} catch (Exceptions e) {
					e.getMessage();
				}

			}
			if (!existeArticulo && esPrecioVentaFloat && esGastosEnvioFloat && tiempoInferior) {
				if (precioVenta.getText().contains(",")) {
					precioVenta.setText(precioVenta.getText().replace(",", "."));
				}
				if (gastosEnvio.getText().contains(",")) {
					gastosEnvio.setText(gastosEnvio.getText().replace(",", "."));
				}
				contro.addArticulo(codigoArticulo.getText(), descripcion.getText(),
						Float.parseFloat(precioVenta.getText()), Float.parseFloat(gastosEnvio.getText()),
						Integer.parseInt(tiempoPreparacion.getText()));
				lblCodigoArticulo.setText("");
				lblPrecioVenta.setText("");
				lblGastosEnvio.setText("");
				lblTiempoPreparacion.setText("");
				Alert mensajeConfirmacionModal = new Alert(AlertType.INFORMATION);
				mensajeConfirmacionModal.setTitle("ARTICULOS");
				mensajeConfirmacionModal.setHeaderText(null);
				mensajeConfirmacionModal.setContentText("Artículo introducido correctamente!");

				mensajeConfirmacionModal.showAndWait();

			} else {
				if (existeArticulo) {
					lblCodigoArticulo.setTextFill(Color.web("red"));
					lblCodigoArticulo.setVisible(true);
					lblCodigoArticulo.setText("El artículo ya existe");
				} else {
					lblCodigoArticulo.setText("");
				}
				if (!esPrecioVentaFloat) {
					lblPrecioVenta.setTextFill(Color.web("red"));
					lblPrecioVenta.setVisible(true);
					lblPrecioVenta.setText("El valor debe ser numérico");
				} else {
					lblPrecioVenta.setText("");
				}
				if (!esGastosEnvioFloat) {
					lblGastosEnvio.setTextFill(Color.web("red"));
					lblGastosEnvio.setVisible(true);
					lblGastosEnvio.setText("El valor debe ser numérico");
				} else {
					lblGastosEnvio.setText("");
				}
				if (!tiempoInferior) {
					lblTiempoPreparacion.setTextFill(Color.web("red"));
					lblTiempoPreparacion.setVisible(true);
					lblTiempoPreparacion.setText("El tiempo de preparación debe ser igual o mayor a 120min");
				} else {
					lblTiempoPreparacion.setText("");
				}
			}
			}else {
				Alert mensajeConfirmacionModal = new Alert(AlertType.INFORMATION);
				mensajeConfirmacionModal.setTitle("ARTÍCULOS");
				mensajeConfirmacionModal.setHeaderText(null);
				mensajeConfirmacionModal.setContentText("Faltan campos por rellenar!");
				mensajeConfirmacionModal.showAndWait();
			}

			break;
		case "Insertar pedido":

			if (!cbArticulos.getSelectionModel().isEmpty() && !cbClientes.getSelectionModel().isEmpty() && !codigoPedido.getText().equals("") 
					&& !unidadesPedido.getText().equals("")) {

				Boolean codigoArticulo = contro.existePedido(Integer.parseInt(codigoPedido.getText()));
				boolean unidadesCorrectas = true;
				int numeroUnidades = Integer.parseInt(unidadesPedido.getText());
				try {

					if (numeroUnidades <= 0 || numeroUnidades > 10) {
						throw new Exceptions(
								"El número de unidades debe ser superior a 0 e inferior a 10. Vuelve a introducirlo:");

					} else {
						System.out.println("El número de unidades ha sido aceptado");
						unidadesCorrectas = false;
					}

				} catch (Exceptions e) {
					System.out.println(e.getMessage());
				}
				
				Cliente c = cbClientes.getValue();
				Articulo a = cbArticulos.getValue();

				if (!codigoArticulo && !unidadesCorrectas) {
					contro.addPedido(Integer.parseInt(codigoPedido.getText()), numeroUnidades, LocalDateTime.now(),
							c.getNif(), a.getCodigo());
					lblErrorCodigoPedido.setText("");
					lblErrorUnidadesPedido.setText("");
					Alert mensajeConfirmacionModal = new Alert(AlertType.INFORMATION);
					mensajeConfirmacionModal.setTitle("PEDIDOS");
					mensajeConfirmacionModal.setHeaderText(null);
					mensajeConfirmacionModal.setContentText("Pedido introducido correctamente!");
					mensajeConfirmacionModal.showAndWait();
				} else {
					if (codigoArticulo) {
						lblErrorCodigoPedido.setTextFill(Color.web("red"));
						lblErrorCodigoPedido.setVisible(true);
						lblErrorCodigoPedido.setText("El pedido ya existe");
					} else {
						lblErrorCodigoPedido.setText("");
					}
					if (unidadesCorrectas) {
						lblErrorUnidadesPedido.setTextFill(Color.web("red"));
						lblErrorUnidadesPedido.setVisible(true);
						lblErrorUnidadesPedido.setText("El número de unidades debe ser superior a 0 e inferior a 10");
					} else {
						lblErrorUnidadesPedido.setText("");
					}
				}
			}else {
				Alert mensajeConfirmacionModal = new Alert(AlertType.INFORMATION);
				mensajeConfirmacionModal.setTitle("PEDIDOS");
				mensajeConfirmacionModal.setHeaderText(null);
				mensajeConfirmacionModal.setContentText("Faltan campos por rellenar!");
				mensajeConfirmacionModal.showAndWait();
			}
			break;
		case "Insertar Cliente":
			
			if (!nif.getText().equals("") && !nombre.getText().equals("") && !domicilio.getText().equals("") 
					&& !email.getText().equals("") && !lblTipoCliente.getSelectionModel().isEmpty()) {
				
				Boolean extisteCliente = contro.existeCliente(nif.getText());
				Boolean existeChar = email.getText().contains("@"); //true
//				String getName = nombre.getText();
//				String getDomicilio = domicilio.getText(); //false
//				String getEmail = email.getText();
				String getTipo_cliente = lblTipoCliente.getValue();
				
				String tipoCliente = "2";
				
				
				if(getTipo_cliente == "Estandar") {
					tipoCliente = "1";
				}
					
				if (!extisteCliente && existeChar) {
					
					contro.addCliente(nombre.getText(), domicilio.getText(), nif.getText(), email.getText(), tipoCliente);
					lblNif.setText("");
					lblEmail.setText("");
					Alert mensajeConfirmacionModal = new Alert(AlertType.INFORMATION);
					mensajeConfirmacionModal.setTitle("CLIENTE");
					mensajeConfirmacionModal.setHeaderText(null);
					mensajeConfirmacionModal.setContentText("El Cliente has ido introduciodo correctamente!");

					mensajeConfirmacionModal.showAndWait();
					
				} else {
					if (extisteCliente) {
						lblNif.setTextFill(Color.web("red"));
						lblNif.setVisible(true);
						lblNif.setText("El cliente ya existe");
					} else {
						lblNif.setText("");
					}

					if (!existeChar) {
						try {
							
							lblEmail.setTextFill(Color.web("red"));
							lblEmail.setVisible(true);
							lblEmail.setText("El email debe contener '@'");
							
							throw new Exceptions("El email debe contener '@' ");

						} catch (Exception e) {
							e.getMessage();
						}
					}

				}	
					
				
			}else {
				Alert mensajeConfirmacionModal = new Alert(AlertType.INFORMATION);
				mensajeConfirmacionModal.setTitle("CLIENTES");
				mensajeConfirmacionModal.setHeaderText(null);
				mensajeConfirmacionModal.setContentText("Faltan campos por rellenar!");
				mensajeConfirmacionModal.showAndWait();
			}
		
		default:
			break;
		}

	}

	/**
	 * controlamos que el numero introducido sea un integer
	 * 
	 * @param numero le pasamos el numero a comprobar
	 * @return retornaremos verdadero o falso dependiendo si es integer o no
	 */
	boolean esInteger(String numero) {
		try {
			Integer.parseInt(numero);
			return true;
		} catch (NumberFormatException err) {
			System.out.println("El carácter debe ser numérico, introduce un número");
			return false;
		}
	}

	/**
	 * controlamos que el numero introducido sea un float
	 * 
	 * @param numero le pasamos el numero a comprobar
	 * @return retornaremos verdadero o falso dependiendo si es float o no
	 */
	boolean esFloat(String numero) {
		try {
			if (numero.contains(",")) {
				numero = numero.replace(",", ".");
			}
			Float.parseFloat(numero);
			return true;
		} catch (NumberFormatException err) {
			System.out.println("El carácter debe ser númerico, introduce un número");
			return false;
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		tkimImageView.setImage(myImage);
		iniciarElementos();
	}

	private void iniciarElementos() {
		
		List<Cliente> clientes = contro.mostrarClientesTodos();
		List<Articulo> articulos = contro.mostrarArticulos();
		ObservableList<Cliente> olClientes = FXCollections.observableArrayList();
		ObservableList<Articulo> olArticulos = FXCollections.observableArrayList();
		olClientes.addAll(clientes);
		olArticulos.addAll(articulos);
		cbClientes.setItems(olClientes);
		cbArticulos.setItems(olArticulos);
		
		ObservableList<String> comboBoxItems = FXCollections.observableArrayList();
		comboBoxItems.add("Estandar");
		comboBoxItems.add("Premium");
		lblTipoCliente.setItems(comboBoxItems);
		
	}
	
	public void cambioTextoComboBoxCliente(ActionEvent event) {
		Cliente c = cbClientes.getValue();
		labelNif.setText(c.getNif());
		labelNombre.setText(c.getNombre());
		labelDomicilio.setText(c.getDomicilio());
		labelEmail.setText(c.getEmail());
		labelTC.setText(c.getTipo_cliente());
		labelCA.setText(String.valueOf(c.getCuota_anual()));
		labelDE.setText(String.valueOf(c.getDescuento_envio()));
	}
	
	public void cambioTextoComboBoxArticulo(ActionEvent event) {
		Articulo a = cbArticulos.getValue();
		labelCodigoArticulo.setText(a.getCodigo());
		labelDescripcion.setText(a.getDescripcion());
		labelPV.setText(String.valueOf(a.getPrecioVenta()));
		labelGE.setText(String.valueOf(a.getGastosEnvio()));
		labelTP.setText(String.valueOf(a.getTiempoPreparacion()));
	}
	
	public void ctb(ActionEvent event) {
		System.out.println("entra");
	}
}