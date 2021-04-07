import { createGlobalStyle } from 'styled-components'

export default createGlobalStyle`
  html, body, #root {
    box-sizing: border-box;
    margin: 0;
    padding: 0;
    height: 100%;
    font-family: "Courier New";
    background-color: var(--seondary-color);
    --primary-color: #f1d145;
    --secondary-color: #86baa1;
    --tertiary-color: #553D36;
    --quarternary-color: 
    --opacity-hover:0.7;
  }
  
  section{
    padding: 0px;
    margin: 0px;
  }
  
  p{
    font-weight: bold;
  }
  
  h5{
    margin: 0;
    padding: 0;
  }
  
  a{
    text-decoration: none;
    color: chocolate;
    :hover {
      opacity: var(--opacity-hover);
    }
  }
  
  .searchBox#css_custom_input::placeholder{
    color: black;
  }

  .searchBox#css_custom_input{
    font-family: "Courier New";
    font-weight: bold;
    color: black;
  }
  
`
