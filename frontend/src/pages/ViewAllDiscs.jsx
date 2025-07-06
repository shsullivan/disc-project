import React, { useEffect, useState } from 'react';
import PageWrapper from '../components/PageWrapper';
import {Link} from "react-router-dom";

function ViewAllDiscs() {
    const [discs, setDiscs] = useState([]);

    useEffect(() => {
        fetch('http://localhost:8080/api/discs')
            .then(response => response.json())
            .then(data => setDiscs(data))
            .catch(error => console.error('Error fetching discs:', error));
    }, []);

    return (
            <div className="table-container">
                <h2>All Discs in System</h2>
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Manufacturer</th>
                        <th>Mold</th>
                        <th>Plastic</th>
                        <th>Color</th>
                        <th>Condition</th>
                        <th>Description</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Phone</th>
                        <th>Found At</th>
                        <th>Returned</th>
                        <th>Sold</th>
                        <th>MSRP</th>
                        <th>Resale</th>
                    </tr>
                    </thead>
                    <tbody>
                    {discs.map(disc => (
                        <tr key={disc.discID}>
                            <td>{disc.discID}</td>
                            <td>{disc.manufacturer}</td>
                            <td>{disc.mold}</td>
                            <td>{disc.plastic}</td>
                            <td>{disc.color}</td>
                            <td>{disc.condition}</td>
                            <td>{disc.description}</td>
                            <td>{disc.contactFirstName}</td>
                            <td>{disc.contactLastName}</td>
                            <td>{disc.contactPhone}</td>
                            <td>{disc.foundAt}</td>
                            <td>{disc.returned ? 'Yes' : 'No'}</td>
                            <td>{disc.sold ? 'Yes' : 'No'}</td>
                            <td>${disc.msrp.toFixed(2)}</td>
                            <td>${disc.resaleValue.toFixed(2)}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
                <div className={"back-link"}><Link to={"/main"}>Main Menu</Link></div>
            </div>
    );
}

export default ViewAllDiscs;
