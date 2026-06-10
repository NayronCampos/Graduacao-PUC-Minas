
import React from 'react';

const CustomLogo: React.FC = () => {
  return (
    <svg width="200" height="200" viewBox="0 0 200 200" xmlns="http://www.w3.org/2000/svg">
      <style>
        {`@import url('https://fonts.googleapis.com/css2?family=Ramadhan+Mubarak&display=swap');
        .text {
          font-family: 'Ramadhan Mubarak', cursive;
          font-size: 100px;
          font-weight: 400;
        }`}
      </style>
      <rect width="200" height="200" fill="#1e4b7a"/>
      <text x="30" y="140" className="text" fill="white">V</text>
      <text x="110" y="140" className="text" fill="#eab308">C</text>
    </svg>
  );
};

export default CustomLogo;
