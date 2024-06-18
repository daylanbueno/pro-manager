import React, { useState } from 'react';
import axios from 'axios';
import { Box, Button, TextField, Typography } from '@mui/material';
import { useNavigate } from 'react-router-dom';

export function Login() {
    const navigate = useNavigate();
    const [loginData, setLoginData] = useState({
        username: '',
        password: '',
    });
    const [error, setError] = useState('');

    const handleInputChange = (e) => {
        setLoginData({
            ...loginData,
            [e.target.name]: e.target.value,
        });
    };

    const handleLogin = async () => {
        try {
            const response = await axios.post('http://localhost:8080/api/auth', {
                login: loginData.username,
                password: loginData.password,
            });
            const { token } = response.data;
            localStorage.setItem('token', token); 
            navigate('/home');
        } catch (error) {
            setError('Invalid username or password');
        }
    };

    return (
        <Box display="flex" height="100vh" justifyContent="center" alignItems="center">
            <Box sx={{ width: '300px', p: 3, boxShadow: 3, borderRadius: 8 }}>
                <Typography variant="h4" gutterBottom sx={{ textAlign: 'center' }}>Login</Typography>
                <TextField
                    fullWidth
                    label="Username"
                    name="username"
                    value={loginData.username}
                    onChange={handleInputChange}
                    variant="outlined"
                    margin="normal"
                />
                <TextField
                    fullWidth
                    type="password"
                    label="Password"
                    name="password"
                    value={loginData.password}
                    onChange={handleInputChange}
                    variant="outlined"
                    margin="normal"
                    error={error !== ''}
                    helperText={error}
                />
                <Button
                    fullWidth
                    onClick={handleLogin}
                    variant="contained"
                    color="primary"
                    sx={{ mt: 2 }}
                >
                    Enter
                </Button>
            </Box>
        </Box>
    );
}
