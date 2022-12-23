package tkim.controlador;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import tkim.modelo.Articulo;
import tkim.modelo.Cliente;
import tkim.modelo.Exceptions;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class OnlineStore extends Application {

	Scanner teclado = new Scanner(System.in);
	Controlador contro = new Controlador();

	// ##############################FORMULARIO INSERTAR ARTICULOS##############################
	@FXML
	private TextField codigoArticulo, descripcion, precioVenta, gastosEnvio, tiempoPreparacion;
	@FXML
	private Label lblCodigoArticulo, lblPrecioVenta, lblGastosEnvio, lblTiempoPreparacion;

	@FXML
	private Button boton;
	// #########################################################################################

	@FXML
	private BorderPane borderPanePedidosPendientesclientes = new BorderPane();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("../vista/onlineStore.fxml"));
		primaryStage.setTitle("Online Store");
		primaryStage.setScene(new Scene(root, 400, 300));
		primaryStage.show();
	}

	public static void main(String[] args) throws Exception {
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		launch(args);
	}

	@SuppressWarnings("unchecked")
	public void handleButtonPress(ActionEvent event) throws IOException {
		String nameButton = ((Button) event.getSource()).getId();
		switch (nameButton) {
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
			tiempoPreparacionColumn.setCellValueFactory(new PropertyValueFactory<Articulo, String>("tiempoPreparacion"));

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

			Scene scene = new Scene(borderPane, 500, 300);
			Stage stage = new Stage();
			stage.setTitle("ARTICULOS");
			stage.setScene(scene);
			stage.show();

			break;
		case "buttonShowClientsAll":
			BorderPane borderPaneClientes = new BorderPane();
			List<Cliente> clientes = contro.mostrarClientesTodos();

			// El String que ponemos entre parentesis en setCellValueFactory es el nombre de
			// la variable de clase de Articulo
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

			Scene sceneClientesTodos = new Scene(borderPaneClientes, 500, 300);
			Stage stageClientesTodos = new Stage();
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

			Scene sceneClientesEstandar = new Scene(borderPaneClientesEstandar, 500, 300);
			Stage stageClientesEstandar = new Stage();
			stageClientesEstandar.setTitle("CLIENTES ESTANDAR");
			stageClientesEstandar.setScene(sceneClientesEstandar);
			stageClientesEstandar.show();
			break;
		case "buttonShowPremiumClients":
			BorderPane borderPaneClientesPremium = new BorderPane();
			List<Cliente> clientesPremium = contro.mostrarClientesPremium();

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
			TableColumn<Cliente, String> descuentoCPEnvioColum = new TableColumn<>("Descuento en el envío");
			descuentoCPEnvioColum.setCellValueFactory(new PropertyValueFactory<Cliente, String>("descuento_envio"));
			
			TableView<VBox> tvButton = new TableView<VBox>();
			TableColumn<VBox, String> buttonColumn = new TableColumn<>("Ver pedidos");
			//buttonColumn.setCellValueFactory(new PropertyValueFactory<Button, String>("dummy"));
			
			tvClientesPremium.getColumns().add(nifCPColumn);
			tvClientesPremium.getColumns().add(nombreCPColumn);
			tvClientesPremium.getColumns().add(domicilioCPColumn);
			tvClientesPremium.getColumns().add(emailCPColumn);
			tvClientesPremium.getColumns().add(tipoClienteCPColumn);
			tvClientesPremium.getColumns().add(cuotaAnualCPColumn);
			tvClientesPremium.getColumns().add(descuentoCPEnvioColum);
			tvButton.getColumns().add(buttonColumn);
			tvClientesPremium.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			tvButton.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			ObservableList<Button> numList = FXCollections.observableArrayList();
			VBox vBoxPedidosPendientes = new VBox();
			for (Cliente clientePremium : clientesPremium) {
				tvClientesPremium.getItems().add(clientePremium);
				Button b = new Button("info");
				b.setId(clientePremium.getNif());
				numList.add(b);
				vBoxPedidosPendientes.getChildren().add(new Button("Ver pedidos"));
			}
			
			
			tvButton.getItems().add(vBoxPedidosPendientes);
			borderPaneClientesPremium.setLeft(tvClientesPremium);
			borderPaneClientesPremium.setRight(tvButton);
			//borderPaneClientesPremium.setRight(vBoxPedidosPendientes);
			Scene sceneClientesPremium = new Scene(borderPaneClientesPremium, 800, 600);

			Stage stageClientesPremium = new Stage();
			stageClientesPremium.setTitle("CLIENTES PREMIUM");
			stageClientesPremium.setScene(sceneClientesPremium);
			stageClientesPremium.show();
			break;
		case "buttonShowPedidoPerClientPendientes":
			try {
				// El String que ponemos entre parentesis en setCellValueFactory es el nombre de
				// la variable de clase de Cliente
				/*TableView<Cliente> tvPPcliente = new TableView<Cliente>();
				TableColumn<Cliente, String> a = new TableColumn<>("Nif");
				a.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nif"));
				TableColumn<Cliente, String> b = new TableColumn<>("Nombre");
				b.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombre"));
				TableColumn<Cliente, String> c = new TableColumn<>("Otro");
				c.setCellValueFactory(new PropertyValueFactory<Cliente, String>("otro"));
				

				tvPPcliente.getColumns().add(a);
				tvPPcliente.getColumns().add(b);
				tvPPcliente.getColumns().add(c);
				
				tvPPcliente.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);*/
				List<Cliente> cPremium = contro.mostrarClientesPremium();
				VBox vBclientes = new VBox();
				for (Cliente clientePremium : cPremium) {
					Label labelCliente = new Label();
					Button b = new Button();
					HBox hbCliente = new HBox();
					b.setText("Ver pedidos");
					b.setId(clientePremium.getNif().toString());
					b.setOnAction(new EventHandler<ActionEvent>() {

				        public void handle(ActionEvent event) {
				        	JOptionPane.showMessageDialog(null, b.getId());
				        }
				    });
					
					labelCliente.setText(clientePremium.getNif());
					hbCliente.getChildren().add(labelCliente);
					hbCliente.getChildren().add(b);
					vBclientes.getChildren().add(hbCliente);
				}
				FXMLLoader fxmlLoaderPedidosPendientes = new FXMLLoader(getClass().getResource("../vista/mostrarPedidosClientePendientes.fxml"));
				Parent root = (Parent) fxmlLoaderPedidosPendientes.load();
				Stage stagePedidosClientePendientes = new Stage();
				borderPanePedidosPendientesclientes.setCenter(vBclientes);
				Scene scenePPcliente = new Scene(borderPanePedidosPendientesclientes, 500, 300);
				stagePedidosClientePendientes.setScene(new Scene(root));
				stagePedidosClientePendientes.setScene(scenePPcliente);
				stagePedidosClientePendientes.setTitle("Pedidos pendientes");
				stagePedidosClientePendientes.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "buttonShowPedidoPerClientEnviados":
			break;
		default:
			break;
		}

	}

	public void submit(ActionEvent event) {
		String botonClicado = boton.getText();
		switch (botonClicado) {
		case "Insertar articulo":
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

			break;
		default:
			break;
		}
	}

	/**
	 * menu de inicio de la aplicacion
	 * 
	 * @throws Exception
	 */

	void inicio() throws Exception {
		boolean salir = false;
		String opcio;
		do {
			System.out.println(" 1. Añadir artículo");
			System.out.println(" 2. Mostrar artículos");
			System.out.println(" 3. Añadir clientes");
			System.out.println(" 4. Mostrar clientes");
			System.out.println(" 5. Mostrar clientes estandar");
			System.out.println(" 6. Mostrar clientes premium");
			System.out.println(" 7. Crear pedido");
			System.out.println(" 8. Eliminar pedido");
			System.out.println(" 9. Mostrar pedidos cliente pendientes de envío");
			System.out.println("10. Mostrar pedidos cliente enviados");
			System.out.println(" 0. Salir de la aplicación");
			opcio = pedirOpcioMenu();
			switch (opcio) {
			case "1":
				addArticulo();
				break;
			case "2":
				mostrarArticulos();
				break;
			case "3":
				addCliente();
				break;
			case "4":
				mostrarClientes();
				break;
			case "5":
				mostrarClientesEstandar();
				break;
			case "6":
				mostrarClientesPremium();
				break;
			case "7":
				addPedido();
				break;
			case "8":
				eliminarPedido();
				break;
			case "9":

				break;
			case "10":

				break;
			case "0":
				salir = true;
			}
		} while (!salir);
	}

	void pausar() {
		String entrada;
		do {
			System.out.println("Presiona intro para ir al menu principal...");
			entrada = teclado.nextLine();
			System.out.println(entrada);
		} while (!entrada.equals(""));
	}

	/**
	 * funcion que devuelve la opcion de menu escogida
	 * 
	 * @return retorna el numero indicad
	 */
	String pedirOpcioMenu() {
		String resp;
		teclado = new Scanner(System.in);
		System.out.print("Elige una opción (1,2,3,4,5,6,7,8,9,10 o 0 (Salir)): ");
		resp = teclado.nextLine();
		if (resp.isEmpty()) {
			resp = " ";
		}
		return resp;

	}

	/**
	 * metodo que creara un articulo
	 * 
	 * @param articulo clase articulo
	 */
	void addArticulo() {

		System.out.println("Codigo: ");
		String codigo = teclado.nextLine();
		if (contro.existeArticulo(codigo)) {
			System.out.println("El codigo " + codigo + " de artículo ya existe.");
			addArticulo();
		} else {

			System.out.println("Descripción: ");
			String descripcion = teclado.nextLine();

			String precioVenta;
			do {
				System.out.println("Precio de venta: ");
				precioVenta = teclado.nextLine();
			} while (!esFloat(precioVenta));

			String gastosEnvio;
			do {
				System.out.println("Gastos de envio: ");
				gastosEnvio = teclado.nextLine();
			} while (!esFloat(gastosEnvio));

			System.out.println("Tiempo de preparación EN MINUTOS: ");
			boolean prep = true;
			int tiempoPreparacion;
			do {
				tiempoPreparacion = teclado.nextInt();
				try {

					if (tiempoPreparacion < 120) {
						throw new Exceptions(
								"El tiempo de preparación no puede ser inferior a 120min. Vuelve a introducirlo:");

					} else {
						if (precioVenta.contains(",")) {
							precioVenta = precioVenta.replace(",", ".");
						}
						if (gastosEnvio.contains(",")) {
							gastosEnvio = gastosEnvio.replace(",", ".");
						}
						System.out.println(contro.addArticulo(codigo, descripcion, Float.parseFloat(precioVenta),
								Float.parseFloat(gastosEnvio), tiempoPreparacion));
						prep = false;
					}

				} catch (Exceptions e) {
					System.out.println(e.getMessage());
				}

			} while (prep);

			System.out.println("");
			pausar();
		}
	}

	/**
	 * metodo que muestra los clientes
	 */
	void mostrarArticulos() {
		// Aqui guardaremos en un arraylist la lista de clientes que nos devolvera el
		// controlador
		// y despues lo mostraremos por pantalla con un foreach
		List<Articulo> articulos = contro.mostrarArticulos();
		if (!articulos.isEmpty()) {
			for (Articulo articulo : articulos) {
				System.out.print("- Código: " + articulo.getCodigo() + " ");
				System.out.print("Descripción: " + articulo.getDescripcion() + " ");
				System.out.print("Precio de venta: " + articulo.getPrecioVenta() + " ");
				System.out.print("Gastos de envio: " + articulo.getGastosEnvio() + " ");
				System.out.print("Tiempo de preparacion: " + articulo.getTiempoPreparacion() + "\n");
			}
		} else {
			System.out.println("Ha habido algun fallo a la hora de recuperar los datos");
		}
		pausar();
	}

	/**
	 * metodo que creara un nuevo cliente especificando su nif, nombre, domicilio,
	 * email y el tipo de cliente que sera, o estandar o premium
	 * 
	 */
	void addCliente() throws Exception {

		System.out.println("Nif: ");
		String nif = teclado.nextLine();
		if (contro.existeCliente(nif)) {
			System.out.println("El cliente con el nif " + nif + " ya existe.");
			addCliente();
		} else {
			System.out.println("Nombre: ");
			String nombre = teclado.nextLine();

			System.out.println("Domicilio: ");
			String domicilio = teclado.nextLine();

			System.out.println("Email: ");
			String email;
			boolean bool = true;

			do {
				email = teclado.nextLine();
				try {

					if (!email.contains("@")) {
						throw new Exceptions("El email debe contener @. Vuelve a introducir su email:");

					} else {
						System.out.println("El email ha sido aceptado");
						bool = false;
					}

				} catch (Exceptions e) {
					System.out.println(e.getMessage());
				}

			} while (bool);

			String tipoCliente;
			System.out.println("Escoge el tipo de cliente: (1) Estandar (2) Premium");
			do {
				tipoCliente = teclado.nextLine();
			} while (!"12".contains(tipoCliente));

			// Aqui enviaremos el nif, nombre, domicilio, email y tipo de cliente al
			// controlador

			System.out.println(contro.addCliente(nombre, domicilio, nif, email, tipoCliente));
			System.out.println("");
			pausar();
		}
	}

	/**
	 * metodo que muestra los clientes
	 */
	void mostrarClientes() {
		// Aqui guardaremos en un arraylist la lista de clientes que nos devolvera el
		// controlador
		// y despues lo mostraremos por pantalla con un foreach
		System.out.println("##########################################################################");
		System.out.println("############################# CLIENTES ###################################");
		System.out.println("##########################################################################");
		System.out.println("");
		for (Cliente cliente : contro.mostrarClientesTodos()) {
			System.out.println(cliente + "\n");
		}
		System.out.println("");
		System.out.println("##########################################################################");
		System.out.println("############################# CLIENTES ###################################");
		System.out.println("##########################################################################");
		System.out.println("");
		pausar();
	}

	void mostrarClientesEstandar() {
		// Aqui guardaremos en un arraylist la lista de clientes estandar que nos
		// devolvera el controlador
		// y despues lo mostraremos por pantalla con un foreach
		System.out.println("##########################################################################");
		System.out.println("######################## CLIENTES ESTANDAR ###############################");
		System.out.println("##########################################################################");
		System.out.println("");
		for (Cliente cliente : contro.mostrarClientesEstandar()) {
			System.out.println(cliente.getNombre() + "\n");
		}
		System.out.println("");
		System.out.println("##########################################################################");
		System.out.println("######################## CLIENTES ESTANDAR ###############################");
		System.out.println("##########################################################################");
		System.out.println("");
		pausar();
	}

	void mostrarClientesPremium() {
		// Aqui guardaremos en un arraylist la lista de clientes premium que nos
		// devolvera el controlador
		// y despues lo mostraremos por pantalla con un foreach
		System.out.println("##########################################################################");
		System.out.println("######################## CLIENTES PREMIUM ###############################");
		System.out.println("##########################################################################");
		System.out.println("");
		for (Cliente cliente : contro.mostrarClientesPremium()) {
			System.out.println(cliente.getNombre() + "\n");
		}
		System.out.println("");
		System.out.println("##########################################################################");
		System.out.println("######################## CLIENTES PREMIUM ###############################");
		System.out.println("##########################################################################");
		System.out.println("");
		pausar();
	}

	/**
	 * metodo que creara un pedido nuevo
	 */
	void addPedido() {

		String nifClientes = "";
		String codigoArticulos = "";
		String nif = "";
		String art = "";

		String numeroPedido;
		do {
			System.out.println("Número de pedido: ");
			numeroPedido = teclado.nextLine();
		} while (!esInteger(numeroPedido));

		if (contro.existePedido(Integer.parseInt(numeroPedido))) {
			System.out.println("Ya existe un pedido con ese código");
			addPedido();
		} else {
			System.out.println("Unidades: ");
			int unidadesPedido;

			boolean unid = true;

			do {
				unidadesPedido = Integer.parseInt(teclado.nextLine());
				try {

					if (unidadesPedido <= 0 || unidadesPedido > 10) {
						throw new Exceptions(
								"El número de unidades debe ser superior a 0 e inferior a 10. Vuelve a introducirlo:");

					} else {
						System.out.println("El número de unidades ha sido aceptado");
						unid = false;
					}

				} catch (Exceptions e) {
					System.out.println(e.getMessage());
				}

			} while (unid);

			System.out.println("Escoge el cliente del pedido.");
			System.out.println("");
			// Aqui llamaremos al controlador para que nos devuelva la lista de clientes y
			// listarlos
			List<Cliente> clientes = contro.mostrarClientesTodos();
			for (Cliente cliente : clientes) {
				System.out.println("nif: " + cliente.getNif() + " nombre: " + cliente.getNombre() + " mail: "
						+ cliente.getEmail());
				nifClientes += cliente.getNif() + ",";
			}

			System.out.println("");
			do {
				System.out.println("Elige un nif de cliente (" + nifClientes + "): ");
				nif = teclado.nextLine();
			} while (!nifClientes.contains(nif + ","));

			System.out.println("Escoge el artículo del pedido.");
			System.out.println("");
			// Aqui llamaremos al controlador para que nos devuelva la lista de articulos y
			// listarlos
			List<Articulo> articulos = contro.mostrarArticulos();
			for (Articulo articulo : articulos) {
				System.out.println("Código: " + articulo.getCodigo() + " descripción: " + articulo.getDescripcion());
				codigoArticulos += articulo.getCodigo() + ",";
			}

			System.out.println("");
			do {
				System.out.println("Elige un código de artículo (" + codigoArticulos + "): ");
				art = teclado.nextLine();
			} while (!codigoArticulos.contains(art + ","));

			System.out.println(
					contro.addPedido(Integer.parseInt(numeroPedido), unidadesPedido, LocalDateTime.now(), nif, art));
			System.out.println("");
			pausar();
		}
	}

	/**
	 * metodo que nos devolvera todos los pedidos enviados
	 */
	void eliminarPedido() {
		// Enviaremos al controlador el pedido que queremos eliminar
		String numeroPedido;
		do {
			System.out.println("Número de pedido: ");
			numeroPedido = teclado.nextLine();
		} while (!esInteger(numeroPedido));

		System.out.println(contro.eliminarPedido(Integer.parseInt(numeroPedido)));
		System.out.println("");
		pausar();
	}

	/**
	 * metodo que nos devolvera todos los pedidos enviados
	 */
	/*
	 * void mostrarPedidosEnviados() { // Aqui llamaremos al controlador para que
	 * nos devuelva la lista de pedidos con // el filtro de enviados // y lo
	 * mostraremos String numeroClientes = "0"; String cliente = "";
	 * System.out.println("Escoge un cliente:"); System.out.println("");
	 * 
	 * // Aqui llamaremos al controlador para que nos devuelva la lista de clientes
	 * y // listarlos for (int i = 0; i <
	 * contro.datos.getClientes().getDato().size(); i++) { System.out.println(i + 1
	 * + ". " + contro.datos.getClientes().getDato().get(i).getNombre() + "\n");
	 * numeroClientes += String.valueOf(i + 1) + ","; } System.out.println("");
	 * 
	 * System.out.println(""); do { System.out.println("Elige el cliente (" +
	 * numeroClientes.substring(1) + "): "); cliente = teclado.nextLine(); } while
	 * (!numeroClientes.contains(cliente));
	 * 
	 * List<Pedido> pedidos = contro.mostrarPedEnviados(Integer.parseInt(cliente));
	 * System.out.println(
	 * "##########################################################################")
	 * ; System.out.
	 * println("######################## PEDIDOS ENVIADOS ################################"
	 * ); System.out.println(
	 * "##########################################################################")
	 * ; System.out.println(""); for (Pedido pedido : pedidos) {
	 * System.out.println(pedido + "\n"); } System.out.println("");
	 * System.out.println(
	 * "##########################################################################")
	 * ; System.out.
	 * println("######################## PEDIDOS ENVIADOS ################################"
	 * ); System.out.println(
	 * "##########################################################################")
	 * ; System.out.println(""); pausar(); }
	 * 
	 *//**
		 * metodo que nos devolvera todos los pedidos pendientes
		 *//*
			 * void mostrarPedidosPendientes() { // Aqui llamaremos al controlador para que
			 * nos devuelva la lista de pedidos con // el filtro de pendientes // y lo
			 * mostraremos String numeroClientes = "0"; String cliente = "";
			 * System.out.println("Escoge un cliente:"); System.out.println("");
			 * 
			 * // Aqui llamaremos al controlador para que nos devuelva la lista de clientes
			 * y // listarlos for (int i = 0; i <
			 * contro.datos.getClientes().getDato().size(); i++) { System.out.println(i + 1
			 * + ". " + contro.datos.getClientes().getDato().get(i).getNombre() + "\n");
			 * numeroClientes += String.valueOf(i + 1) + ","; } System.out.println("");
			 * 
			 * System.out.println(""); do { System.out.println("Elige un cliente (" +
			 * numeroClientes.substring(1) + "): "); cliente = teclado.nextLine(); } while
			 * (!numeroClientes.contains(cliente));
			 * 
			 * List<Pedido> pedidos =
			 * contro.mostrarPedPendientes(Integer.parseInt(cliente)); System.out.println(
			 * "##########################################################################")
			 * ; System.out.
			 * println("######################## PEDIDOS PENDIENTES ##############################"
			 * ); System.out.println(
			 * "##########################################################################")
			 * ; System.out.println(""); for (Pedido pedido : pedidos) {
			 * System.out.println(pedido + "\n"); } System.out.println("");
			 * System.out.println(
			 * "##########################################################################")
			 * ; System.out.
			 * println("######################## PEDIDOS PENDIENTES ##############################"
			 * ); System.out.println(
			 * "##########################################################################")
			 * ; System.out.println(""); pausar(); }
			 */

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
}