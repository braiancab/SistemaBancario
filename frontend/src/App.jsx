import { BrowserRouter, Routes, Route } from 'react-router-dom';

import Login from './usuarios/Login';
import Registro from './usuarios/Registro';
import Dashboard from './panel/Dashboard';
import Transferencias from "./panel/Transferencias";


function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Ruta principal: cuando el cliente entra a la página, ve el Login */}
        <Route path="/" element={<Login />} />
        
        {/* Ruta secundaria: cuando va a /registro, ve el formulario nuevo */}
        <Route path="/registro" element={<Registro />} />

        <Route path="/panel" element={<Dashboard />} />

        <Route path="/transferencias" element={<Transferencias />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;