import React, { useState } from "react";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import axios from "axios";
import Modal from "./Modal"; // Import Modal component
import "./Table.css";
import config from "./config";

const fetchData = async ({ queryKey }) => {
  const [, page, size] = queryKey;
  const response = await axios.get(`${config.API_BASE_URL}/estore/api/position_type/findAll?page=${page}&size=${size}`);
  return response.data;
};

const PositionTypeTab = () => {
  const queryClient = useQueryClient();
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(5);
  const [isErrorModalOpen, setIsErrorModalOpen] = useState(false);
  const [isAddModalOpen, setIsAddModalOpen] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const [newPositionType, setNewPositionType] = useState({
    name: "",
  });

  const { data, error, isLoading } = useQuery({
    queryKey: ["positionTypes", page, size],
    queryFn: fetchData,
    keepPreviousData: true,
    onError: (err) => {
      setErrorMessage(`Error: ${err.response ? err.response.data : err.message}`);;
      setIsErrorModalOpen(true); // Open modal on error
    },
  });

  const addMutation = useMutation({
    mutationFn: async (entity) => {
      await axios.post(`${config.API_BASE_URL}/estore/api/position_type/add`, entity, {
        headers: {
          'Content-Type': 'application/json', 
        }
      });
    },
    onSuccess: () => {
      queryClient.invalidateQueries(["positionTypes"]);
      setIsAddModalOpen(false); // Close modal on success
    },
    onError: (err) => {
      setErrorMessage(`Error: ${err.response ? err.response.data : err.message}`);;
      setIsErrorModalOpen(true);
    },
  });

  const deleteMutation = useMutation({
    mutationFn: async (id) => {
      await axios.delete(`${config.API_BASE_URL}/estore/api/position_type/delete?id=${id}`);
    },
    onSuccess: () => {
      queryClient.invalidateQueries(["positionTypes"]);
    },
    onError: (err) => {
      // Log the full error to the console for debugging.
      console.error("Error occurred while deleting:", err);
      setErrorMessage(`Error: ${err.response ? err.response.data : err.message}`);
      setIsErrorModalOpen(true);
    },
  });

  const handleAdd = () => {
    addMutation.mutate(newPositionType);
  };

  if (isLoading) return <p>Loading...</p>;

  return (
    <div>
      <h2>PositionTypes</h2>

      {/* Add Button */}
      <button onClick={() => setIsAddModalOpen(true)}>Add</button>

      {/* Employee Table */}
      <table className="type-table">
        <thead>
          <tr>
            <th>id</th>
            <th>Name</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {data?.map((positionType) => (
            <tr key={positionType.id}>
              <td>{positionType.id}</td>
              <td>{positionType.name}</td>
              <td>
                <button onClick={() => deleteMutation.mutate(positionType.id)}>Delete</button>
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

      {isAddModalOpen && (
        <div className="modal">
          <h3>Add New</h3>
          <input type="text" placeholder="Name" value={newPositionType.name} onChange={(e) => setNewPositionType({ ...newPositionType, name: e.target.value })} />
          <button onClick={handleAdd}>Submit</button>
          <button onClick={() => setIsAddModalOpen(false)}>Cancel</button>
        </div>
      )}
    </div>
  );
};

export default PositionTypeTab;
