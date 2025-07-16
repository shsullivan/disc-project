import React, { useState } from 'react';
import PageWrapper from '../components/PageWrapper';
import { Link } from 'react-router-dom';

function SearchByLastName() {
    const [phone, setPhone] = useState('');
    const [results, setResults] = useState([]);
    const [message, setMessage] = useState('');

    const handleSearch = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch(`http://localhost:8080/api/discs/search/phone?phone=${phone}`);
            if (response.ok) {
                const data = await response.json();
                setResults(data);
                setMessage(data.length ? '' : 'No results found.');
            } else {
                setMessage('Search failed.');
            }
        } catch (error) {
            setMessage('Error: ' + error.message);
        }
    };

    return (
        <PageWrapper>
            <div className="form-container">
                <h2>Search by Phone Number</h2>
                <form onSubmit={handleSearch}>
                    <label htmlFor="phone">Phone Number:</label>
                    <input
                        type="text"
                        id="phone"
                        value={phone}
                        onChange={(e) => setPhone(e.target.value)}
                        required
                    />
                    <button type="submit">Search</button>
                </form>
                {message && <p>{message}</p>}
                {results.length > 0 && (
                    <table>
                        <thead>
                        <tr>
                            <th>Disc ID</th>
                            <th>Manufacturer</th>
                            <th>Mold</th>
                            <th>Plastic</th>
                            <th>Color</th>
                            <th>Condition</th>
                            <th>Contact Name</th>
                            <th>Phone</th>
                        </tr>
                        </thead>
                        <tbody>
                        {results.map(disc => (
                            <tr key={disc.discID}>
                                <td>{disc.discID}</td>
                                <td>{disc.manufacturer?.manufacturer}</td>
                                <td>{disc.mold?.mold}</td>
                                <td>{disc.plastic}</td>
                                <td>{disc.color}</td>
                                <td>{disc.condition}</td>
                                <td>{disc.contact?.firstName} {disc.contact?.lastName}</td>
                                <td>{disc.contact?.phone}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                )}
                <div className="back-link">
                    <Link to="/main">← Back to Main Menu</Link>
                </div>
            </div>
        </PageWrapper>
    );
}

export default SearchByLastName;
