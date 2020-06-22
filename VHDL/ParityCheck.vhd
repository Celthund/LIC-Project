----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    22:58:41 05/20/2020 
-- Design Name: 
-- Module Name:    ParityCheck - Behavioral 
-- Project Name: 
-- Target Devices: 
-- Tool versions: 
-- Description: 
--
-- Dependencies: 
--
-- Revision: 
-- Revision 0.01 - File Created
-- Additional Comments: 
--
----------------------------------------------------------------------------------
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity ParityCheck is
	 GENERIC (
			WIDTH : POSITIVE 
	 );
    Port ( CLK : in  STD_LOGIC;
           RST : in  STD_LOGIC;
           Data : IN std_logic_vector(WIDTH-1 downto 0);
           Err : OUT std_logic_vector(WIDTH-1 downto 0));
end ParityCheck;

architecture Behavioral of ParityCheck is
	COMPONENT Register_D
		GENERIC (
			WIDTH : POSITIVE 
		);
		PORT(
			CLK : IN std_logic;
			RST : IN std_logic;
			D : IN std_logic_vector(WIDTH-1 downto 0);          
			Q : OUT std_logic_vector(WIDTH-1 downto 0)
		);
	END COMPONENT;
	
	SIGNAL Q : std_logic_vector(WIDTH-1 downto 0);
	SIGNAL D1 : std_logic_vector(WIDTH-1 downto 0);
	
begin
	RegisterD : Register_D 
	GENERIC MAP(
		WIDTH => 1
	)
	PORT MAP(
			CLK => CLK,
			RST => RST,
			D => D1,
			Q => Q
	);
	Err <= NOT Q;
	D1 <= Q xor Data;
end Behavioral;

