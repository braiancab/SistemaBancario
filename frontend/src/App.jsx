import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import  Login  from "./usuarios/Login";
import  Registro  from "./usuarios/Registro";
import  Dashboard  from "./panel/Dashboard"; 
import  Transferencias  from "./panel/Transferencias";
import  CrearCuenta  from "./panel/CrearCuenta";
import  BarraNavegacion  from "./componentes/BarraNavegacion";
import HistorialTransferencias from "./panel/HistorialTransferencias";

function App() {
  
  const estaAutenticado = () => {
    return localStorage.getItem("token") !== null;
  };

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/registro" element={<Registro />} />

        
        <Route
          path="/dashboard" 
          element={estaAutenticado() ? <><BarraNavegacion /><Dashboard /></> : <Navigate to="/login" />} 
        />
        <Route 
          path="/crear-cuenta" 
          element={estaAutenticado() ? <><BarraNavegacion /><CrearCuenta /></> : <Navigate to="/login" />} 
        />
        <Route 
          path="/transferencias" 
          element={estaAutenticado() ? <><BarraNavegacion /><Transferencias /></> : <Navigate to="/login" />} 
        />
        <Route 
          path="/movimientos" 
          element={estaAutenticado() ? <><BarraNavegacion /><HistorialTransferencias /></> : <Navigate to="/login" />} 
        />
        {/* Si entra a cualquier otra ruta rara, lo mandamos al login */}
        <Route path="*" element={<Navigate to="/login" />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;