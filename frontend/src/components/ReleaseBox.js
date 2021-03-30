import styled from 'styled-components/macro'
import {Link} from "react-router-dom";
import ReleaseDescription from "../components/ReleaseDescription";

export default function ReleaseBox ({format, artistName}){

    return (
        <ReleaseContainer>
            <h3l>Most popular/wanted {format}</h3l>
            <Link to={`artist/${artistName}/${format}s`} style={{textDecoration: 'none'}}>
                <h3r>see more {format}s ...</h3r>
            </Link>
        </ReleaseContainer>
    )
}

const ReleaseContainer = styled.div`

  display: flex;
  flex-direction: column;
  background-color: var(--secondary-color);
  padding: 20px;

  h3l {
    padding: 0px;
    margin: 0px;
    width: 100%;
    font-size: 1.0em;
    color: #636e72;
  }

  h3r {
    padding: 0px;
    margin: 0px;
    width: 100%;
    font-size: 0.6em;
    color: #636e72;
  }
  
`