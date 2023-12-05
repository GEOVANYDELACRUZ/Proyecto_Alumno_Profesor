<?php
$hostname_localhost="localhost";
$database_localhost="id21315300_project";
$username_localhost="id21315300_gdelac";
$password_localhost="769gDTC*";

$json=array();

    if(isset($_GET["correo"]) && isset($_GET["contra"])){
        $conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
		if ($conexion->connect_error) {
		  die("Connection failed: " . $conexion->connect_error);
		}

        $correo=$_GET['correo'];
        $contra=$_GET['contra'];
        $respuesta="call ingresar_profesor('$correo','$contra')";
		$resultado=mysqli_query($conexion,$respuesta);
		
        if($registro=mysqli_fetch_array($resultado)){
            
                $result["IDProfesor"]=$registro['IDProfesor'];
			    $result["profNombre"]=$registro['profNombre'];
			    $result["profApellidos"]=$registro['profApellidos'];
			    $result["profCorreo"]=$registro['profCorreo'];
			    $result["profContra"]=$registro['profContra'];
			    $json['tblProfesor'][]=$result;
                
           
        }
        else{
			  echo "Error: " . $sql . "<br>" . mysqli_error($conexion);
			$result["IDProfesor"]=0;
			$result["profNombre"]='No registra';
			$result["profApellidos"]='No registra';
			$result["profCorreo"]='No registra';
			$result["profContra"]='No registra';
			$json['tblProfesor'][]=$result;
        }
		mysqli_close($conexion);
		echo json_encode($json);
    }
    else{
            $resultar["IDProfesor"]=0;
            $resultar["profNombre"]='WS no retorna';
            $resultar["profApellidos"]='WS no retorna';
            $resultar["profCorreo"]='WS no retorna';
            $resultar["profContra"]='WS no retorna';
            $json['tblProfesor'][]=$resultar;
            echo json_encode($json);
      }
?>