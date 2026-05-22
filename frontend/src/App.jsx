import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import  Login  from "./usuarios/Login";
import  Registro  from "./usuarios/Registro";
import  Dashboard  from "./panel/Dashboard"; 
import  Transferencias  from "./panel/Transferencias";
import  CrearCuenta  from "./panel/CrearCuenta";
import  BarraNavegacion  from "./componentes/BarraNavegacion";
import HistorialTransferencias from "./panel/HistorialTransferencias";
import PieDePagina from "./componentes/PieDePagina";
import SolicitarExtraccion from "./panel/SolicitarExtraccion";

function App() {
  
  const estaAutenticado = () => {
    return localStorage.getItem("token") !== null;
  };

  return (
    <BrowserRouter>
      
      <div className="d-flex flex-column min-vh-100"> 
        <div className="flex-grow-1">
          <Routes>
            <Route path="/login" element={<Login />} />
            <Route path="/registro" element={<Registro />} />

            
            <Route 
              path="/dashboard" 
              element={estaAutenticado() ? <><BarraNavegacion /><Dashboard /><PieDePagina /></> : <Navigate to="/login" />} 
            />
            <Route 
              path="/crear-cuenta" 
              element={estaAutenticado() ? <><BarraNavegacion /><CrearCuenta /><PieDePagina /></> : <Navigate to="/login" />} 
            />
            <Route 
              path="/transferencias" 
              element={estaAutenticado() ? <><BarraNavegacion /><Transferencias /><PieDePagina /></> : <Navigate to="/login" />} 
            />
            <Route 
              path="/movimientos" 
              element={estaAutenticado() ? <><BarraNavegacion /><HistorialTransferencias /><PieDePagina /></> : <Navigate to="/login" />} 
            />

            <Route path="/extraccion" 
             element={estaAutenticado() ? <><BarraNavegacion /><SolicitarExtraccion /><PieDePagina /></> : <Navigate to="/login" />} 



             />
            <Route path="*" element={<Navigate to="/login" />} />
          </Routes>
        </div>
      </div>
    </BrowserRouter>
  );
}

export default App;