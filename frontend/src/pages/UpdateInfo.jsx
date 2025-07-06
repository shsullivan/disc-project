import React, { useState } from 'react';
import PageWrapper from '../components/PageWrapper';
import { Link } from 'react-router-dom';

function UpdateDisc() {
    const [discID, setDiscID] = useState('');
    const [formData, setFormData] = useState(null);
    const [message, setMessage] = useState('');

    const fetchDisc = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/discs/${discID}`);
            if (response.ok) {
                const data = await response.json();
                setFormData(data);
                setMessage('');
            } else {
                setMessage('Disc not found.');
                setFormData(null);
            }
        } catch (error) {
            setMessage('Error fetching disc.');
            setFormData(null);
        }
    };

    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: type === 'checkbox' ? checked : value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch(`http://localhost:8080/api/discs/${discID}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(formData)
            });

            if (response.ok) {
                setMessage('Disc updated successfully!');
            } else {
                setMessage('Failed to update disc.');
            }
        } catch (error) {
            setMessage('Error: ' + error.message);
        }
    };

    return (
        <PageWrapper>
            <div className="form-container">
                <h2>Update Disc</h2>
                {!formData && (
                    <div>
                        <label>Disc ID: </label>
                        <input
                            type="text"
                            value={discID}
                            onChange={(e) => setDiscID(e.target.value)}
                            required
                        />
                        <button onClick={fetchDisc}>Load Disc</button>
                    </div>
                )}

                {formData && (
                    <form onSubmit={handleSubmit}>
                        {[
                            'manufacturer', 'mold', 'plastic', 'color',
                            'condition', 'description', 'contactFirstName',
                            'contactLastName', 'contactPhone', 'foundAt', 'MSRP'
                        ].map(field => (
                            <div key={field}>
                                <label>{field.charAt(0).toUpperCase() + field.slice(1)}</label>
                                <input
                                    type={field === 'condition' || field === 'MSRP' ? 'number' : 'text'}
                                    name={field}
                                    value={formData[field]}
                                    onChange={handleChange}
                                />
                            </div>
                        ))}

                        <div>
                            <label>Returned</label>
                            <input
                                type="checkbox"
                                name="returned"
                                checked={formData.returned}
                                onChange={handleChange}
                            />
                        </div>

                        <div>
                            <label>Sold</label>
                            <input
                                type="checkbox"
                                name="sold"
                                checked={formData.sold}
                                onChange={handleChange}
                            />
                        </div>

                        <button type="submit">Update Disc</button>
                    </form>
                )}

                {message && <p>{message}</p>}

                <div className="back-link">
                    <Link to="/main">← Back to Main Menu</Link>
                </div>
            </div>
        </PageWrapper>
    );
}

export default UpdateDisc;
