import React, { useState } from "react";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import axios from "axios";
import Modal from "./Modal"; // Import Modal component
import "./Table.css";
import config from "./config";

const fetchData = async ({ queryKey }) => {
  const [, page, size] = queryKey;
  const response = await axios.get(`${config.API_BASE_URL}/estore/api/electro_shop/findAll?page=${page}&size=${size}`);
  return response.data;
};

const ElectroShopTab = () => {
  const queryClient = useQueryClient();
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(5);
  const [isErrorModalOpen, setIsErrorModalOpen] = useState(false);
  const [isAddModalOpen, setIsAddModalOpen] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const [newElectroShop, setNewElectroShop] = useState({
    shop: "",
    electroItem: "",
    count: "",
  });

  const { data, error, isLoading } = useQuery({
    queryKey: ["electroShops", page, size],
    queryFn: fetchData,
    keepPreviousData: true,
    onError: (err) => {
      setErrorMessage(`Error: ${err.response ? err.response.data : err.message}`);;
      setIsErrorModalOpen(true); // Open modal on error
    },
  });

  const addMutation = useMutation({
    mutationFn: async (entity) => {
      await axios.post(`${config.API_BASE_URL}/estore/api/electro_shop/add`, entity, {
        headers: {
          'Content-Type': 'application/json', 
        }
      });
    },
    onSuccess: () => {
      queryClient.invalidateQueries(["electroShops"]);
      setIsAddModalOpen(false); // Close modal on success
    },
    onError: (err) => {
      setErrorMessage(`Error: ${err.response ? err.response.data : err.message}`);;
      setIsErrorModalOpen(true);
    },
  });

  const deleteMutation = useMutation({
    mutationFn: async (id) => {
      await axios.delete(`${config.API_BASE_URL}/estore/api/electro_shop/delete?id=${id}`);
    },
    onSuccess: () => {
      queryClient.invalidateQueries(["electroShops"]);
    },
    onError: (err) => {
      // Log the full error to the console for debugging.
      console.error("Error occurred while deleting:", err);
      setErrorMessage(`Error: ${err.response ? err.response.data : err.message}`);
      setIsErrorModalOpen(true);
    },
  });

  const handleAdd = () => {
    addMutation.mutate(newElectroShop);
  };

  if (isLoading) return <p>Loading...</p>;

  return (
    <div>
      <h2>Electro Shops</h2>

      {/* Add Button */}
      <button onClick={() => setIsAddModalOpen(true)}>Add</button>

      {/* Employee Table */}
      <table className="type-table">
        <thead>
          <tr>
            <th>Shop</th>
            <th>ElectroItem</th>
            <th>Count</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {data?.map((electroShop) => (
            <tr>
              <td>{electroShop.shop}</td>
              <td>{electroShop.electroItem}</td>
              <td>{electroShop.count}</td>
              <td>
                <button onClick={() => deleteMutation.mutate(electroShop.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {/* Pagination Controls */}
      <button disabled={page === 0} onClick={() => setPage(page - 1)}>Previous</button>
      <button onClick={() => setPage(page + 1)}>Next</button>
      <select onChange={(e) => setSize(Number(e.target.value))} value={size}>
        <option value="5">5</option>
        <option value="10">10</option>
        <option value="20">20</option>
      </select>

      {/* Error Modal */}
      {isErrorModalOpen && <Modal message={errorMessage} onClose={() => setIsErrorModalOpen(false)} />}

      {/* Add Employee Modal */}
      {isAddModalOpen && (
        <div className="modal">
          <h3>Add New</h3>
          <input type="number" placeholder="Shop" value={newElectroShop.shop} onChange={(e) => setNewElectroShop({ ...newElectroShop, shop: e.target.value })} />
          <input type="number" placeholder="ElectroItem" value={newElectroShop.electroItem} onChange={(e) => setNewElectroShop({ ...newElectroShop, electroItem: e.target.value })} />
          <input type="number" placeholder="Count" value={newElectroShop.count} onChange={(e) => setNewElectroShop({ ...newElectroShop, count: e.target.value })} />
          <button onClick={handleAdd}>Submit</button>
          <button onClick={() => setIsAddModalOpen(false)}>Cancel</button>
        </div>
      )}
    </div>
  );
};

export default ElectroShopTab;
