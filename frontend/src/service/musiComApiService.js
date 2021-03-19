import axios from 'axios'

const baseUrl = '/api'

export const getArtistInfo = (artistName) =>
    axios.get(baseUrl + `/artist/${artistName}`).then((response) => response.data)