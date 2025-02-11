import React from "react";
import { Routes, Route, NavLink } from "react-router-dom";
import ShopTab from "./ShopTab";
import EmployeeTab from "./EmployeeTab";
import ElectroItemTab from "./ElectroItemTab";
import ElectroTypeTab from "./ElectroTypeTab";
import PositionTypeTab from "./PositionTypeTab";
import PurchaseTypeTab from "./PurchaseTypeTab";
import PurchaseTab from "./PurchaseTab";
import ElectroShopTab from "./ElectroShopTab";
import ElectroEmployeeTab from "./ElectroEmployeeTab";
import UploadZipTab from "./UploadZipTab";

const App = () => {
  return (
    <div className="container">
      <nav>
        <NavLink to="/shops" className="tab">Shops</NavLink>
        <NavLink to="/employees" className="tab">Employees</NavLink>
        <NavLink to="/electroItems" className="tab">ElectroItems</NavLink>
        <NavLink to="/electroTypes" className="tab">ElectroTypes</NavLink>
        <NavLink to="/positionTypes" className="tab">PositionTypes</NavLink>
        <NavLink to="/purchaseTypes" className="tab">PurchaseTypes</NavLink>
        <NavLink to="/purchases" className="tab">Purchases</NavLink>
        <NavLink to="/electroShops" className="tab">ElectroShops</NavLink>
        <NavLink to="/electroEmployees" className="tab">ElectroEmployees</NavLink>
        <NavLink to="/uploadZip" className="tab">UploadZip</NavLink>
      </nav>
      <Routes>
        <Route path="/shops" element={<ShopTab />} />
        <Route path="/employees" element={<EmployeeTab />} />
        <Route path="/electroItems" element={<ElectroItemTab />} />
        <Route path="/electroTypes" element={<ElectroTypeTab />} />
        <Route path="/positionTypes" element={<PositionTypeTab />} />
        <Route path="/purchaseTypes" element={<PurchaseTypeTab />} />
        <Route path="/purchases" element={<PurchaseTab />} />
        <Route path="/electroShops" element={<ElectroShopTab />} />
        <Route path="/electroEmployees" element={<ElectroEmployeeTab />} />
        <Route path="/uploadZip" element={<UploadZipTab />} />
      </Routes>
    </div>
  );
};

export default App;
