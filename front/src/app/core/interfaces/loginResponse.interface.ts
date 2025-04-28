/**
 * Response shape for resdponse returned by the backend on login.
 *
 * @remarks
 * This interface is used to type the HTTP response received after a successful
 * authentication request.
 *
 * @property token – The JSON Web Token (JWT) issued by the server upon successful login.
 *
 */
export interface LoginResponse {
  token: string;
}
