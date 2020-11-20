import React from 'react';
import './Header.css'

const Header:React.FC = () => {
    return (
        <div className= "page-wrap">
            <div className = "nav-style">
                <nav className="navbar navbar-expand-lg navbar-light">
                    <a className="navbar-brand" href="#">Navbar</a>
                    
                    <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"/>
                    </button>
                    <div className="collapse navbar-collapse" id="navbarNav">
                        <ul className="navbar-nav ml-auto">
                            <li className="nav-item active">
                                <a className="nav-link" href="#">Home <span className="sr-only">(current)</span></a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link" href="#">Features</a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link" href="#">Pricing</a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link disabled" href="#" tabIndex={-1} aria-disabled="true">Disabled</a>
                            </li>
                        </ul>
                    </div>
                    {/* <div className = "btn-search">
                        <a href = "#"><img width='20px' height = '20px' src = {searchicon} alt = "search-icon"/></a>
                    </div> */}
                </nav>

            </div>
        </div>
    );
};

export default Header;
