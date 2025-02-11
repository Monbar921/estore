import React, { useState } from "react";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import axios from "axios";
import Modal from "./Modal"; // Import Modal component
import "./Table.css";
import config from "./config";

const fetchShops = async ({ queryKey }) => {
  const [, page, size] = queryKey;
  const response = await axios.get(`${config.API_BASE_URL}/estore/api/shop/findAll?page=${page}&size=${size}`);
  return response.data;
};

const ShopTab = () => {
  const queryClient = useQueryClient();
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(5);
  const [isErrorModalOpen, setIsErrorModalOpen] = useState(false);
  const [isAddModalOpen, setIsAddModalOpen] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const [purchaseType, setPurchaseType] = useState(1);
  const [shopForSum, setShopForSum] = useState(1);
  const [sellingSum, setSellingSum] = useState(0);
  const [iShowSellingSum, setIsShowSellingSum] = useState(false);
  const [newShop, setNewShop] = useState({
    name: "",
    address: "",
  });

  const { data, error, isLoading } = useQuery({
    queryKey: ["shops", page, size],
    queryFn: fetchShops,
    keepPreviousData: true,
    onError: (err) => {
      setErrorMessage(`Error: ${err.response ? err.response.data : err.message}`);;
      setIsErrorModalOpen(true); // Open modal on error
    },
  });

  const fetchSellingSum = async ([shopForSum, purchaseType]) => { 
    const response = await axios.get(
      `${config.API_BASE_URL}/estore/api/shop/sum_by_ptype?shopId=${shopForSum}&purchaseTypeId=${purchaseType}`
    );
    console.log(response);
    return response.data;
  };

  const addMutation = useMutation({
    mutationFn: async (shop) => {
      await axios.post(`${config.API_BASE_URL}/estore/api/shop/add`, shop, {
        headers: {
          'Content-Type': 'application/json', 
        }
      });
    },
    onSuccess: () => {
      queryClient.invalidateQueries(["shops"]);
      setIsAddModalOpen(false); // Close modal on success
    },
    onError: (err) => {
      setErrorMessage(`Error: ${err.response ? err.response.data : err.message}`);;
      setIsErrorModalOpen(true);
    },
  });

  const deleteMutation = useMutation({
    mutationFn: async (id) => {
      await axios.delete(`${config.API_BASE_URL}/estore/api/shop/delete?id=${id}`);
    },
    onSuccess: () => {
      queryClient.invalidateQueries(["shops"]);
    },
    onError: (err) => {
      // Log the full error to the console for debugging.
      console.error("Error occurred while deleting:", err);
      setErrorMessage(`Error: ${err.response ? err.response.data : err.message}`);
      setIsErrorModalOpen(true);
    },
  });

  const handleAdd = () => {
    addMutation.mutate(newShop);
  };

  const handleFetchSellingSum = async () => { 
    try {
      // Pass an array directly
      const result = await fetchSellingSum([shopForSum, purchaseType]);
      setSellingSum(result);
      setIsShowSellingSum(true);
    } catch (error) {
      console.error("Error fetching best sellers:", error);
    }
  };

  if (isLoading) return <p>Loading...</p>;

  return (
    <div>
      <h2>Shops</h2>

      {/* Add Button */}
      <button onClick={() => setIsAddModalOpen(true)}>Add</button>
      <div>The sum by purchase type</div>
      <table className="type-table">
        <tbody>
          <tr >
            <td>
              <div>Shop</div>
              <input 
                type="number" 
                placeholder="shop" 
                value={shopForSum} onChange={(e) => setShopForSum(e.target.value)}
              />
              <div>Purchase type</div>
              <input 
                type="number" 
                placeholder="Purchase type" 
                value={purchaseType} onChange={(e) => setPurchaseType(e.target.value)}
              />
            </td>
            <td>
              <button onClick={handleFetchSellingSum}>Get</button>
            </td>
          </tr>
        </tbody>
      </table>

      {iShowSellingSum && (
        <div>
          <div/>
          <div>The sum is {sellingSum}</div>
        </div>
      )}
      
      {!iShowSellingSum && (
        <table className="type-table">
          <thead>
            <tr>
              <th>id</th>
              <th>Name</th>
              <th>Address</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {data?.map((shop) => (
              <tr key={shop.id}>
                <td>{shop.id}</td>
                <td>{shop.name}</td>
                <td>{shop.address}</td>
                <td>
                  <button onClick={() => deleteMutation.mutate(shop.id)}>Delete</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}

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
          <input type="text" placeholder="Name" value={newShop.name} onChange={(e) => setNewShop({ ...newShop, name: e.target.value })} />
          <input type="text" placeholder="Address" value={newShop.address} onChange={(e) => setNewShop({ ...newShop, address: e.target.value })} />
          <button onClick={handleAdd}>Submit</button>
          <button onClick={() => setIsAddModalOpen(false)}>Cancel</button>
        </div>
      )}
    </div>
  );
};

export default ShopTab;
