<?php
$hostname_localhost="localhost";
$database_localhost="id21315300_project";
$username_localhost="id21315300_gdelac";
$password_localhost="769gDTC*";

$json=array();

    if(isset($_GET["cod"])){
        $conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
        $cod=$_GET['cod'];
        $consulta="SELECT 
                P.IDProfesor,
                C.curNombre, 
                AC.IDAlumC,
                CONCAT( A.alumAPaterno, ' ', A.alumNombres) as Alumno,
                CN.IDCursNota, CN.cursNota, CN.cursCriterio,CN.cursNComment, CN.cursNRespuesta
                FROM tblProfesor P 
                INNER JOIN tblCursProf CP on CP.IDProfesor=P.IDProfesor
                INNER JOIN tblCurso C on C.IDCurso=CP.IDCurso
                INNER JOIN tblAlumCurs AC on AC.IDCurso=C.IDCurso
                INNER JOIN tblCurs_Nota CN on CN.IDAlumC=AC.IDAlumC
                INNER JOIN tblAlumno A on A.DNI=AC.DNI
                where P.IDProfesor = $cod and CN.cursNComment is NOT NULL;";
        $resultado=mysqli_query($conexion,$consulta);
		
        if($resp=mysqli_fetch_array($resultado)){
		    $resultado=mysqli_query($conexion,$consulta);
            while($registro=mysqli_fetch_array($resultado)){
                $result["IDProfesor"]=$registro['IDProfesor'];
			    $result["curNombre"]=$registro['curNombre'];
			    $result["IDAlumC"]=$registro['IDAlumC'];
                $result["Alumno"]=$registro['Alumno'];
			    $result["IDCursNota"]=$registro['IDCursNota'];
			    $result["cursNota"]=$registro['cursNota'];
                $result["cursCriterio"]=$registro['cursCriterio'];
			    $result["cursNComment"]=$registro['cursNComment'];
			    $result["cursNRespuesta"]=$registro['cursNRespuesta'];
			    $json['tblCurso'][]=$result;
                
           }
        }
        else{
            $result["IDProfesor"]=0;
		    $result["curNombre"]='No registra';
		    $result["IDAlumC"]='No registra';
            $result["Alumno"]='No registra';
		    $result["notCriterio"]='No registra';
		    $result["notValor"]='No registra';
            $result["notDescrip"]='No registra';
		    $result["cursNComment"]='No registra';
		    $result["cursNRespuesta"]='No registra';
		    $json['tblCurso'][]=$result;
        }
		mysqli_close($conexion);
		echo json_encode($json);
    }
    else{
        
            $result["IDProfesor"]=-1;
		    $result["curNombre"]='WS no retorna';
		    $result["IDAlumC"]='WS no retorna';
            $result["Alumno"]='WS no retorna';
		    $result["notCriterio"]='WS no retorna';
		    $result["notValor"]='WS no retorna';
            $result["notDescrip"]='WS no retorna';
		    $result["cursNComment"]='WS no retorna';
		    $result["cursNRespuesta"]='WS no retorna';
		    $json['tblCurso'][]=$result;
            echo json_encode($json);
      }
?>