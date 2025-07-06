import React, { useState } from 'react';
import axios from 'axios';
import PageWrapper from "../components/PageWrapper.jsx";
import {Link} from "react-router-dom";

function AddDisc() {
    const [formData, setFormData] = useState({
        manufacturer: '',
        mold: '',
        plastic: '',
        color: '',
        condition: '',
        description: '',
        contactFirstName: '',
        contactLastName: '',
        contactPhone: '',
        foundAt: '',
        msrp: ''
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await axios.post('http://localhost:8080/api/discs', {
                ...formData,
                condition: parseInt(formData.condition),
                msrp: parseFloat(formData.msrp)
            });
            alert("Disc added successfully!");
        } catch (error) {
            console.log(error);
            alert("Error adding disc: " + error.response?.data?.message ||
                    error.response?.statusText || error.message);
        }
    };

    return (
        <PageWrapper>
            <div className="form-container">
                <h2>Add a New Disc</h2>
                <form onSubmit={handleSubmit}>
                    {[
                        { name: 'manufacturer' },
                        { name: 'mold' },
                        { name: 'plastic' },
                        { name: 'color' },
                        { name: 'condition', type: 'number' },
                        { name: 'description' },
                        { name: 'contactFirstName' },
                        { name: 'contactLastName' },
                        { name: 'contactPhone' },
                        { name: 'foundAt' },
                        { name: 'msrp', label: 'MSRP', type: 'number' }
                    ].map(({ name, type = 'text', label }) => (
                        <div key={name}>
                            <label>{label || name.charAt(0).toUpperCase() + name.slice(1)}</label>
                            <input
                                type={type}
                                name={name}
                                value={formData[name]}
                                onChange={handleChange}
                                required
                            />
                        </div>
                    ))}
                    <button type="submit">Add Disc</button>
                </form>
                <div className={"back-link"}><Link to={"/main"}>Main Menu</Link></div>
            </div>
        </PageWrapper>
    );
}

export default AddDisc;
