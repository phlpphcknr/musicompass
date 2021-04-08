import { createGlobalStyle } from 'styled-components'

export default createGlobalStyle`
  html, body, #root {
    box-sizing: border-box;
    margin: 0;
    padding: 0;
    height: 100%;
    font-family: "Courier New";
    background-color: var(--seondary-color);
    --primary-color: #F1D145;
    --secondary-color: #86BAA1;
    --tertiary-color: #553D36;
    --quarternary-color: #27187E;
    --quinary-color: #3AB795;
    
    --opacity-hover:0.7;
  }
  
  button{
    font-size: 16px;
    font-family: inherit;
    font-weight: bold;
    margin: 15px 0px 0px 0px;
    padding: 8px;
    border: 0;
    color: var(--secondary-color);
    background-color: var(--tertiary-color);
  }

/*  .warning{
    text-align: center;
    margin: 15px 30px 0px 30px;
    color: var(--quarternary-color);
  }*/
  
  section{
    padding: 0px;
    margin: 0px;
  }
  
  p{
    font-weight: bold;
  }
  
  span{
    padding: 0px;
    margin: 0px;
    font-weight: bold;
  }

  h1{
    margin: 0;
    padding: 0;
  }
  
  h5{
    margin: 0;
    padding: 0;
  }
  
  a{
    text-decoration: none;
    color: var(--quarternary-color);
    :hover {
      opacity: var(--opacity-hover);
    }
  }
  
  .searchBox#css_custom_input::placeholder{
    color: var(--tertiary-color);
  }

  .searchBox#css_custom_input{
    font-family: "Courier New";
    font-weight: bold;
    color: var(--tertiary-color);
  }
  
`
