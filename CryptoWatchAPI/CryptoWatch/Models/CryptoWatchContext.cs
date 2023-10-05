using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

namespace CryptoWatch.Models
{
    public partial class CryptoWatchContext : DbContext
    {
        public CryptoWatchContext()
        {
        }

        public CryptoWatchContext(DbContextOptions<CryptoWatchContext> options)
            : base(options)
        {
        }

        public virtual DbSet<Comment> Comments { get; set; } = null!;
        public virtual DbSet<TrackingList> TrackingLists { get; set; } = null!;
        public virtual DbSet<User> Users { get; set; } = null!;

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Comment>(entity =>
            {
                entity.HasKey(e => e.IdComment);

                entity.ToTable("Comment");

                entity.Property(e => e.IdComment).HasColumnName("ID_Comment");

                entity.Property(e => e.Com)
                    .HasMaxLength(1000)
                    .IsUnicode(false);

                entity.Property(e => e.UsersId).HasColumnName("Users_ID");
            });

            modelBuilder.Entity<TrackingList>(entity =>
            {
                entity.HasKey(e => e.IdTrackingList);

                entity.ToTable("TrackingList");

                entity.Property(e => e.IdTrackingList).HasColumnName("ID_TrackingList");

                entity.Property(e => e.NameCrypto)
                    .HasMaxLength(500)
                    .IsUnicode(false)
                    .HasColumnName("Name_Crypto");

                entity.Property(e => e.UsersId).HasColumnName("Users_ID");
            });

            modelBuilder.Entity<User>(entity =>
            {
                entity.HasKey(e => e.IdUsers);

                entity.HasIndex(e => e.EmailUsers, "UQ_Email_Users")
                    .IsUnique();

                entity.HasIndex(e => e.LoginUsers, "UQ_Login_Users")
                    .IsUnique();

                entity.HasIndex(e => e.NicknameUsers, "UQ_Nickname_Users")
                    .IsUnique();

                entity.HasIndex(e => e.PasswordUsers, "UQ_Password_Users")
                    .IsUnique();

                entity.Property(e => e.IdUsers).HasColumnName("ID_Users");

                entity.Property(e => e.EmailUsers)
                    .HasMaxLength(500)
                    .IsUnicode(false)
                    .HasColumnName("Email_Users");

                entity.Property(e => e.LoginUsers)
                    .HasMaxLength(500)
                    .IsUnicode(false)
                    .HasColumnName("Login_Users");

                entity.Property(e => e.NicknameUsers)
                    .HasMaxLength(500)
                    .IsUnicode(false)
                    .HasColumnName("Nickname_Users");

                entity.Property(e => e.PasswordUsers)
                    .HasMaxLength(500)
                    .IsUnicode(false)
                    .HasColumnName("Password_Users");
            });

            OnModelCreatingPartial(modelBuilder);
        }

        partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
    }
}
