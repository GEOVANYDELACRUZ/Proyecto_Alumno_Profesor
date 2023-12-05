<?php
$hostname_localhost="localhost";
$database_localhost="id21315300_project";
$username_localhost="id21315300_gdelac";
$password_localhost="769gDTC*";

$json=array();

    if(isset($_GET["dni"])){
        $conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
        $dni=$_GET['dni'];
        $consulta="SELECT * FROM tblAlumno A 
        INNER JOIN tblAlumCurs AC on A.DNI = AC.DNI
        INNER JOIN tblCurso C on C.IDCurso=AC.IDCurso
        where A.DNI like $dni";
        $resultado=mysqli_query($conexion,$consulta);
		
        if($resp=mysqli_fetch_array($resultado)){
		    $resultado=mysqli_query($conexion,$consulta);
            while($registro=mysqli_fetch_array($resultado)){
                $result["IDCurso"]=$registro['IDCurso'];
			    $result["IDAlumC"]=$registro['IDAlumC'];
			    $result["curNombre"]=$registro['curNombre'];
			    $json['tblCurso'][]=$result;
                
           }
        }
        else{
			$result["IDCurso"]=-1;
			$result["IDAlumC"]='No registra';
			$result["curNombre"]='No registra';
			$json['tblCurso'][]=$result;
        }
		mysqli_close($conexion);
		echo json_encode($json);
    }
    else{
            $resultar["IDCurso"]=-1;
            $resultar["IDAlumC"]='WS no retorna';
            $resultar["curNombre"]='WS no retorna';
            $json['tblCurso'][]=$resultar;
            echo json_encode($json);
      }
?>