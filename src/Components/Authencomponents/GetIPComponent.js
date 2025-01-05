import React, { useState, useEffect } from 'react';

const GetIPComponent = () => {
    const [ip, setIp] = useState("");

    // Fetch IP when the component loads
    useEffect(() => {
        const getIp = async () => {
            try {
                const response = await fetch("http://localhost:8080/api/auth/ip");
                if (!response.ok) {
                    throw new Error("Failed to fetch IP");
                }
                const ipText = await response.text();
                setIp(ipText);  // Set only the IP address
            } catch (error) {
                setIp("Error fetching IP");
            }
        };
        getIp();
    }, []);

    return <>{ip}</>;
}

export default GetIPComponent;
