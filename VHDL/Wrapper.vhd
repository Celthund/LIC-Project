----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    20:51:38 06/03/2020 
-- Design Name: 
-- Module Name:    Wrapper - Behavioral 
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

entity Wrapper is
end Wrapper;

architecture Behavioral of Wrapper is
	COMPONENT SerialReceiver
		Port( 
			RST: in STD_LOGIC;
			CLK : in  STD_LOGIC;
			KAck : in  STD_LOGIC;
			KVal: out STD_LOGIC;
			K : out  STD_LOGIC_VECTOR (3 downto 0);
			PENC_IN: in  STD_LOGIC_VECTOR (3 downto 0);
			DEC_OUT : out STD_LOGIC_VECTOR (2 downto 0)
	 );
	END COMPONENT;
	COMPONENT KeyboardReader
		Port( 
			CLK : in  STD_LOGIC;
			RST : in  STD_LOGIC;
			DAV : in  STD_LOGIC;
			ACK : in  STD_LOGIC;
			DAC : out  STD_LOGIC;
			DVal : out  STD_LOGIC;
			D : in STD_LOGIC_VECTOR (3 downto 0);
			Q : out STD_LOGIC_VECTOR (3 downto 0)
	 );
	END COMPONENT;
begin

	SerialReceive: SerialReceiver
		MAP PORT (
			
	);
	
end Behavioral;

