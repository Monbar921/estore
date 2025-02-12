import React, { useState } from "react";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import axios from "axios";
import Modal from "./Modal"; // Import Modal component
import config from "./config";

const UploadZipTab = () => {
  const queryClient = useQueryClient();
  const [isErrorModalOpen, setIsErrorModalOpen] = useState(false);
  const [file, setFile] = useState(null);
  const [errorMessage, setErrorMessage] = useState("");

  const addMutation = useMutation({
    mutationFn: async (file) => {
      await axios.post(`${config.API_BASE_URL}/estore/api/file/zip/upload`, file, {
        headers: {
          "Content-Type": "multipart/form-data",
        }
      });
    },
    onSuccess: () => {
      setErrorMessage(`Ok`);;
      setIsErrorModalOpen(true);
    },
    onError: (err) => {
      console.error("Error occurred while uploading:", err);
      setErrorMessage(`Error: ${err.response ? err.response.data : err.message}`);;
      setIsErrorModalOpen(true);
    },
  });

  const handleFileChange = (event) => {
    setFile(event.target.files[0]); // Get the selected file
  };

  const handleUpload = async () => {
    if (!file) {
      setErrorMessage("Please select a file first!");
      return;
    }

    const formData = new FormData();
    formData.append("file", file); // Key "file" must match backend parameter

    addMutation.mutate(formData);
  };

  return (
    <div>
      <h2>UploadZip</h2>

      {/* Add Button */}
      <button onClick={handleUpload}>Upload</button>
      <input
        type="file"
        accept=".zip"
        onChange={handleFileChange}
      />
      {/* Error Modal */}
      {isErrorModalOpen && <Modal message={errorMessage} onClose={() => setIsErrorModalOpen(false)} />}
    </div>
  );
};

export default UploadZipTab;
