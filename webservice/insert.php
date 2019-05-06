<?php

require 'conexion.php';

$arrayJson = array();

if(isset($_GET["dni"]) && isset($_GET["nombre"]) 
	&& isset($_GET["sexo"]) && isset($_GET["email"]) 
	&& isset($_GET["ciudad"]) && isset($_GET["profesion"])){

	$dni = $_GET['dni'];
	$nombre = $_GET['nombre'];
	$sexo = $_GET['sexo'];
	$email = $_GET['email'];
	$ciudad = $_GET['ciudad'];
	$profesion = $_GET['profesion'];

	$conexion = mysqli_connect($HostServer, $HostUser, $HostPass, $Database);

	$insert = "INSERT INTO datos_usuario(dni, nombre, sexo, email, ciudad, profesion)
	           VALUES ('$dni','$nombre','$sexo','$email','$ciudad','$profesion')";
 
	$result_insert= mysqli_query($conexion, $insert);

	if($result_insert){
		$consultaSQL = "SELECT * FROM datos_usuario WHERE dni = '{dni}'";
		$resultado = mysqli_query($conexion, $consultaSQL);

		if($registro = mysqli_fetch_array($resultado)){
			$arrayJson['datos_usuario'][]=$registro;
		}
		mysqli_close($conexion);
		echo json_encode($arrayJson);

		}else{
			$result["dni"]="$dni";
			$result["nombre"]="$nombre";
			$result["sexo"]="$sexo";
			$result["email"]="$email";
			$result["ciudad"]="$ciudad";
			$result["profesion"]="$profesion";

			$arrayJson['registro_duplicado'][]=$result;
			echo json_encode($arrayJson);
		}
	}

