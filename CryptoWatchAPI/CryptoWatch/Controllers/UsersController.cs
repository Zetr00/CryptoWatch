using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using CryptoWatch.Models;
using PetPortal;

namespace CryptoWatch.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UsersController : ControllerBase
    {
        private readonly CryptoWatchContext _context;

        public UsersController(CryptoWatchContext context)
        {
            _context = context;
        }

        // GET: api/Users
        [HttpGet]
        public async Task<ActionResult<IEnumerable<User>>> GetUsers()
        {
            return await _context.Users.ToListAsync();
        }

        // GET: api/Users/5
        [HttpGet("{id}")]
        public async Task<ActionResult<User>> GetUser(int? id)
        {
            var user = await _context.Users.FindAsync(id);

            if (user == null)
            {
                return NotFound();
            }

            return user;
        }

        // PUT: api/Users/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("{id}")]
        public async Task<IActionResult> PutUser(int? id, User user)
        {
            if (id != user.IdUsers)
            {
                return BadRequest();
            }

            _context.Entry(user).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!UserExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        // POST: api/Users
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult<User>> PostUser(User user)
        {
            _context.Users.Add(user);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetUser", new { id = user.IdUsers }, user);
        }

        // DELETE: api/Users/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteUser(int? id)
        {
            var user = await _context.Users.FindAsync(id);
            if (user == null)
            {
                return NotFound();
            }

            _context.Users.Remove(user);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool UserExists(int? id)
        {
            return _context.Users.Any(e => e.IdUsers == id);
        }

        [Route("reg")]
        [HttpPost]
        public async Task<ActionResult<User>> RegistrationUsers([FromBody] User obj)
        {
            MD5Hash MD5Haash = new MD5Hash();

            obj.IdUsers = null;
            string Salt = "asd123";

            if (_context.Users == null)
            {
                return Problem("Entity set 'Users' is null.");
            }

            obj.PasswordUsers = MD5Haash.GetHash(obj.PasswordUsers + Salt);

            _context.Users.Add(obj);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetUsers", new { id = obj.IdUsers }, obj);
        }

        [Route("auth")]
        [HttpPost]
        public async Task<ActionResult<User>> AuthorizationUsers([FromBody] User user)
        {
            MD5Hash MD5Haash = new MD5Hash();
            string Salt = "asd123";

            var users = await _context.Users.Where(x => x.LoginUsers == user.LoginUsers).FirstOrDefaultAsync();
            if (users != null)
            {
                var currentHash = MD5Haash.GetHash(user.PasswordUsers + Salt);
                if (currentHash == users.PasswordUsers)
                {
                    return Ok(users);
                }
                else
                {
                    return BadRequest();
                }
            }
            else
            {
                return BadRequest();
            }
        }
    }
}
