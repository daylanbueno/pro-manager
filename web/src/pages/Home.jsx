import React, { useState, useEffect } from "react";
import {
  Box,
  Typography,
  Card,
  TextField,
  Button,
  Switch,
  FormControlLabel,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Select,
  MenuItem,
  InputLabel,
  Modal,
  IconButton,
} from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import axios from "axios";

export function Home() {
  const initialFormState = {
    id: null,
    name: "",
    price: "",
    categoryId: "",
    description: "",
    available: false,
  };

  const [formData, setFormData] = useState(initialFormState);
  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [modalOpen, setModalOpen] = useState(false);
  const [deleteProductId, setDeleteProductId] = useState(null);
  const [editProductId, setEditProductId] = useState(null);

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const token = localStorage.getItem('token');
        const response = await axios.get('http://localhost:8080/api/categories', {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setCategories(response.data); // Assuming response.data is an array of categories
      } catch (error) {
        console.error('Error fetching categories:', error);
        // Handle error fetching categories (e.g., set default categories or show error message)
      }
    };

    fetchCategories();
  }, []); // Empty dependency array ensures this effect runs once on component mount

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const token = localStorage.getItem('token');
        const response = await axios.get('http://localhost:8080/api/products', {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setProducts(response.data); // Assuming response.data is an array of products
      } catch (error) {
        console.error('Error fetching products:', error);
        // Handle error fetching products (e.g., set default products or show error message)
      }
    };

    fetchProducts();
  }, []); // Empty dependency array ensures this effect runs once on component mount

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  const handleFormSubmit = () => {
    console.log(formData);
    if (formData.id) {
      editProduct(formData); // Call editProduct function if formData has an id (indicating edit mode)
    } else {
      saveProduct(formData); // Call saveProduct function if formData does not have an id (indicating new product creation)
    }
    setFormData(initialFormState);
  };

  const saveProduct = async (productData) => {
    try {
      const token = localStorage.getItem('token');
      const response = await axios.post('http://localhost:8080/api/products', productData, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      console.log('Product saved:', response.data); // Assuming backend returns saved product data
      // Assuming response.data contains the newly saved product object
      setProducts([...products, response.data]); // Update products state with the newly saved product
    } catch (error) {
      console.error('Error saving product:', error);
      // Handle error saving product (e.g., show error message to user)
    }
  };

  const editProduct = async (productData) => {
    try {
      const token = localStorage.getItem('token');
      const response = await axios.put(`http://localhost:8080/api/products/${productData.id}`, productData, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      console.log('Product edited:', response.data); // Assuming backend returns edited product data
      // Assuming response.data contains the updated product object
      const updatedProducts = products.map((product) =>
        product.id === response.data.id ? response.data : product
      );
      setProducts(updatedProducts); // Update products state with the updated product
    } catch (error) {
      console.error('Error editing product:', error);
      // Handle error editing product (e.g., show error message to user)
    }
  };

  const openDeleteModal = (productId) => {
    setDeleteProductId(productId);
    setModalOpen(true);
  };

  const closeDeleteModal = () => {
    setDeleteProductId(null);
    setModalOpen(false);
  };

  const deleteProduct = async () => {
    try {
      const token = localStorage.getItem('token');
      await axios.delete(`http://localhost:8080/api/products/${deleteProductId}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      console.log('Product deleted:', deleteProductId);
      const updatedProducts = products.filter((product) => product.id !== deleteProductId);
      setProducts(updatedProducts); // Update products state after deletion
      closeDeleteModal();
    } catch (error) {
      console.error('Error deleting product:', error);
      // Handle error deleting product (e.g., show error message to user)
    }
  };

  const openEditForm = (productId) => {
    const productToEdit = products.find((product) => product.id === productId);
    if (productToEdit) {
      setFormData({
        id: productToEdit.id,
        name: productToEdit.name,
        price: productToEdit.price,
        categoryId: productToEdit.categoryId,
        description: productToEdit.description,
        available: productToEdit.available,
      });
      setEditProductId(productId);
    }
  };

  const cancelEdit = () => {
    setFormData(initialFormState);
    setEditProductId(null);
  };

  return (
    <Box height="95vh" sx={{ padding: 2 }}>
      <Typography variant="h4" sx={{ textAlign: "center", marginY: 2 }}>
        Product Registration
      </Typography>

      <Box
        display="flex"
        justifyContent="center"
        alignItems="flex-start"
        gap={4}
      >
        <Card sx={{ p: 3, width: "100%", boxShadow: 3 }}>
          <Typography variant="h6" gutterBottom>
            {editProductId ? "Edit Product" : "Register New Product"}
          </Typography>

          <Box display="flex" flexDirection="column" gap={2}>
            <Box display="flex" gap={1} sx={{ maxWidth: "600px" }}>
              <TextField
                size="small"
                fullWidth
                label="Name"
                name="name"
                value={formData.name}
                onChange={handleChange}
              />
              <TextField
                size="small"
                fullWidth
                label="Price"
                name="price"
                type="number"
                value={formData.price}
                onChange={handleChange}
              />
            </Box>
            <InputLabel id="category-label">Category</InputLabel>
            <Select
              labelId="category-label"
              id="category"
              fullWidth
              name="categoryId"
              value={formData.categoryId}
              onChange={handleChange}
            >
              {categories.map((category) => (
                <MenuItem key={category.id} value={category.id}>
                  {category.name}
                </MenuItem>
              ))}
            </Select>
            <TextField
              size="small"
              fullWidth
              label="Description"
              name="description"
              value={formData.description}
              onChange={handleChange}
            />
            <Box display="flex" flexDirection="column" alignItems="flex-end">
              <FormControlLabel
                control={<Switch color="primary" />}
                label="Available"
                labelPlacement="start"
                name="available"
                checked={formData.available}
                onChange={handleChange}
              />
              {editProductId ? (
                <Box>
                  <Button
                    variant="contained"
                    color="primary"
                    onClick={handleFormSubmit}
                    style={{ marginRight: 10 }}
                  >
                    Update
                  </Button>
                  <Button variant="outlined" onClick={cancelEdit}>
                    Cancel
                  </Button>
                </Box>
              ) : (
                <Button
                  variant="contained"
                  color="primary"
                  onClick={handleFormSubmit}
                >
                  Register
                </Button>
              )}
            </Box>
          </Box>
        </Card>
      </Box>

      <Box mt={4}>
        <Typography variant="h6" sx={{ textAlign: "center", marginBottom: 2 }}>
          Product List
        </Typography>
        <TableContainer component={Paper}>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>Name</TableCell>
                <TableCell align="right">Price</TableCell>
                <TableCell align="right">Category</TableCell>
                <TableCell>Description</TableCell>
                <TableCell align="right">Available</TableCell>
                <TableCell>Action</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {products.map((product) => (
                <TableRow key={product.id}>
                  <TableCell component="th" scope="row">
                    {product.name}
                  </TableCell>
                  <TableCell align="right">${product.price}</TableCell>
                  <TableCell align="right">
                    {
                      categories.find((cat) => cat.id === product.categoryId)
                        ?.name
                    }
                  </TableCell>
                  <TableCell>{product.description}</TableCell>
                  <TableCell align="right">
                    {product.available ? "Yes" : "No"}
                  </TableCell>
                  <TableCell>
                    <IconButton
                      color="primary"
                      aria-label="edit product"
                      onClick={() => openEditForm(product.id)}
                    >
                      <EditIcon />
                    </IconButton>
                    <IconButton
                      color="error"
                      aria-label="delete product"
                      onClick={() => openDeleteModal(product.id)}
                    >
                      <DeleteIcon />
                    </IconButton>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </Box>

      <Modal
        open={modalOpen}
        onClose={closeDeleteModal}
        aria-labelledby="delete-product-modal"
        aria-describedby="modal to confirm product deletion"
      >
        <Box
          sx={{
            position: "absolute",
            top: "50%",
            left: "50%",
            transform: "translate(-50%, -50%)",
            width: 400,
            bgcolor: "background.paper",
            boxShadow: 24,
            p: 4,
            textAlign: "center",
          }}
        >
          <Typography variant="h6" gutterBottom>
            Are you sure you want to delete this product?
          </Typography>
          <Box mt={2}>
            <Button
              variant="contained"
              onClick={deleteProduct}
              color="error"
              style={{ marginRight: 10 }}
            >
              Delete
            </Button>
            <Button variant="outlined" onClick={closeDeleteModal}>
              Cancel
            </Button>
          </Box>
        </Box>
      </Modal>
    </Box>
  );
}
